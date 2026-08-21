package dev.gerardomarquez.mail_whatsapp_send.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubFileResponse;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RepositoryEntity;
import dev.gerardomarquez.mail_whatsapp_send.repositories.RepositoryCrud;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

@Service
public class ImplementationServiceGitHubWebHook implements ServiceGitHubWebHook {

    @Autowired
    private ServicePostsCrud postsCrud;

    @Autowired
    private ServiceRebuildAndDeploy serviceRebuildAndDeploy;

    @Autowired
    private ImplementationServiceGitHub serviceGitHub;

    @Autowired
    private RepositoryCrud repositoryCrud;

    @Value("${github.webhook.secret}")
    private String secret;

    /**
     * {@inheritDoc}
     */
    @Override
    public void pushEventWebHook(String json, String signature) {
        String expected = new String();
        try {
            expected = Constants.calculateSignature(json, signature, Constants.GITHUB);    
        } catch (Exception e) {
            System.out.println(e.getMessage() );
        }
        
        if(!expected.equals(secret) ){
            System.out.println("Secreto invalido, no hay coincidencia");
            return;
        }
            
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            String ref = jsonNode.get("ref").asText();
            if(!ref.equals("refs/heads/main") ){
                System.out.println("No es la rama main, no se hace nada");
                return;
            }
            String repoFullName = jsonNode.path("repository").path("full_name").asText();
            String[] repoParts = repoFullName.split("/");
            String owner = repoParts[0];
            String repo = repoParts[1];

            JsonNode commits = jsonNode.get("commits");
            for (JsonNode commit : commits) {
                JsonNode added = commit.get("added");
                JsonNode removed = commit.get("removed");

                added.forEach(
                    (file) -> {
                        if(!file.asText().endsWith(".md") ){
                            System.out.println("No es un archivo markdown, no se hace nada");
                            return;
                        }
                        
                        Optional<RepositoryEntity> optRepositoryEntity = repositoryCrud.findByOwnerAndName(owner, repoFullName);
                        
                        if(!optRepositoryEntity.isPresent() ){
                            System.out.println("No se encontro el repositorio en la base de datos, no se hace nada");
                            return;
                        }
                        
                        RepositoryEntity repository = optRepositoryEntity.get();
                        GitHubFileResponse serviceGitHubResponse = serviceGitHub.getFileAndSha(
                            repository.getOwner(),
                            repository.getName(),
                            file.asText(),
                            repository.getDefaultBranch(),
                            repository.getGithubToken()
                        );

                        PostEntity penultimatePost = postsCrud.findLastPost();
                        PostEntity lastPost = postsCrud.insertOnePost(
                            jsonNode.path("repository").path("owner").path("name").asText(),
                            jsonNode.path("repository").path("owner").path("name").asText(),
                            jsonNode.get("ref").asText(),
                            file.asText()
                        );
                        Optional<RelationPostEntity> optPenultimateRelationPost =  penultimatePost
                            .getRelationPost()
                            .stream()
                            .filter(it -> !it.getIsInPostList() )
                            .findFirst();
                        
                        if(optPenultimateRelationPost.isPresent() ) {
                            RelationPostEntity penultimateRelationPost = optPenultimateRelationPost.get();
                            penultimateRelationPost.setNextPost(lastPost);
                            postsCrud.insertOrUpdateOneRelationPost(penultimateRelationPost);
                        }

                        Set<RelationPostEntity> setLastRelationPost = lastPost.getRelationPost();
                        if(setLastRelationPost.isEmpty() ){
                            RelationPostEntity lastRelationPost = new RelationPostEntity(
                                null,
                                lastPost,
                                penultimatePost,
                                null,
                                false
                            );
                            postsCrud.insertOrUpdateOneRelationPost(lastRelationPost);
                        }

                        System.out.println("AGREGADO: " + file.asText() );
                    }
                );

                removed.forEach(
                    (file) -> {
                        PostEntity postToDelete = postsCrud.findOnePostByFilePath(file.asText() );
                        // Como el post se elimina se debe modificar primero la relacion que tiene este post,
                        // el post anterior ahora apunta al posterior del que se elimina y el posterior apunta
                        // al anterior del que se eliminta como listas enlazadas, esto se hace en un for porque
                        // hay 2 relaciones, si el post esta en una lista (el previo y el siguiente
                        // post de la lista) y la 2da relacion que es el previo y el siguiente por fecha
                        for(RelationPostEntity it : postToDelete.getRelationPost() ){
                            PostEntity previousPost = it.getPreviousPost();
                            PostEntity nextPost = it.getNextPost();

                            Set<RelationPostEntity> previousRelationPost = previousPost.getRelationPost();
                            for(RelationPostEntity jt : previousRelationPost){
                                jt.setNextPost(it.getNextPost() );
                                postsCrud.insertOrUpdateOneRelationPost(jt);
                            }

                            Set<RelationPostEntity> nextRelationPost = nextPost.getRelationPost();
                            for(RelationPostEntity jt : nextRelationPost){
                                postsCrud.insertOrUpdateOneRelationPost(jt);
                            }

                            postsCrud.deleteOneRelationPost(it);
                        }
                        postsCrud.deleteOne(file.asText() );


                        System.out.println("ELIMINADO: " + file.asText() );
                    }
                );

            }

            serviceRebuildAndDeploy.rebuildAndDeploy();
        } catch (Exception e) {
            System.out.println("Hubo un error en el parseo del json");
        }
    }

}
