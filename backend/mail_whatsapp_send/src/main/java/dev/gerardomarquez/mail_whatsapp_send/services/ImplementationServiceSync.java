package dev.gerardomarquez.mail_whatsapp_send.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.gerardomarquez.mail_whatsapp_send.dtos.FrontMatter;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubCreateResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubFileResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitTreeResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.NoteInfoResponse;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostSyncEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RepositoryEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.TagEntity;
import dev.gerardomarquez.mail_whatsapp_send.errors.GitHubException;
import dev.gerardomarquez.mail_whatsapp_send.errors.HedgeDocException;
import dev.gerardomarquez.mail_whatsapp_send.errors.SyncException;
import dev.gerardomarquez.mail_whatsapp_send.repositories.HedgeDocDataBaseCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.PostsCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.PostsSyncCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.RelationsPostsCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.RepositoryCrud;
import dev.gerardomarquez.mail_whatsapp_send.repositories.TagsCrud;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;
import dev.gerardomarquez.mail_whatsapp_send.utils.Methods;

/**
 * Implementación del servicio orquestador de sincronización.
 * Coordina las llamadas a ServiceGitHub y ServiceHedgeDoc
 * para ejecutar el pull y el push de contenido Markdown.
 */
@Service
public class ImplementationServiceSync implements ServiceSync {
    private final ServiceGitHub serviceGitHub;
    private final ServiceHedgeDoc serviceHedgeDoc;
    private final PostsSyncCrud postSyncCrud;
    private final MessageSource messageSource;
    private final HedgeDocDataBaseCrud hedgeDocDataBaseCrud;
    private final RepositoryCrud repositoryCrud;
    private final TagsCrud tagsCrud;
    private final PostsCrud postsCrud;
    private final ImplementationServiceSocialNetwork serviceSocialNetwork;
    private final ImplementationServicePostsCrud servicePostsCrud;
    private final RelationsPostsCrud relationsPostsCrud;

    private static final Logger log = LoggerFactory.getLogger(ImplementationServiceSync.class);

    /**
     * @param serviceGitHub   Servicio para interactuar con la API de GitHub
     * @param serviceHedgeDoc Servicio para interactuar con la API de HedgeDoc
     * @param postSyncCrud    Repositorio JPA de posts_sync
     * @param messageSource   Para obtener mensajes de error desde messages.properties
     */
    public ImplementationServiceSync(
        ServiceGitHub serviceGitHub,
        ServiceHedgeDoc serviceHedgeDoc,
        PostsSyncCrud postSyncCrud,
        MessageSource messageSource,
        HedgeDocDataBaseCrud hedgeDocDataBaseCrud,
        RepositoryCrud repositoryCrud,
        TagsCrud tagsCrud,
        PostsCrud postsCrud,
        ImplementationServiceSocialNetwork serviceSocialNetwork,
        ImplementationServicePostsCrud servicePostsCrud,
        RelationsPostsCrud relationsPostsCrud
    ) {
        this.serviceGitHub = serviceGitHub;
        this.serviceHedgeDoc = serviceHedgeDoc;
        this.postSyncCrud = postSyncCrud;
        this.messageSource = messageSource;
        this.hedgeDocDataBaseCrud = hedgeDocDataBaseCrud;
        this.repositoryCrud = repositoryCrud;
        this.tagsCrud = tagsCrud;
        this.postsCrud = postsCrud;
        this.serviceSocialNetwork = serviceSocialNetwork;
        this.servicePostsCrud = servicePostsCrud;
        this.relationsPostsCrud = relationsPostsCrud;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String pull(Integer idPost) {

        PostSyncEntity postSync = postSyncCrud.findById(idPost)
                .orElseThrow(
                    () -> new SyncException(
                        messageSource.getMessage(
                            Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                            new Object[]{idPost},
                            LocaleContextHolder.getLocale()
                        )
                    )
                );

        RepositoryEntity repository = postSync.getRepository();

        try {
            GitHubFileResponse serviceGitHubResponse = serviceGitHub.getFileAndSha(
                repository.getOwner(),
                repository.getName(),
                postSync.getFilePath(),
                repository.getDefaultBranch(),
                repository.getGithubToken()
            );

            String content = serviceGitHubResponse.content();
            String sha = serviceGitHubResponse.sha();

            NoteInfoResponse newNote = serviceHedgeDoc.createNote(content);

            String newNoteId = hedgeDocDataBaseCrud.getNoteId(newNote);

            String oldNoteId = postSync.getHedgedocNoteId();

            postSync.setHedgedocNoteId(newNoteId);
            postSync.setGithubSha(sha);
            postSync.setLastSyncedAt(LocalDateTime.now() );
            postSyncCrud.save(postSync);

            hedgeDocDataBaseCrud.deleteOneNote(oldNoteId);

            return newNoteId;

        } catch (GitHubException | HedgeDocException e) {
            log.error("Error during pull operation for post ID {}: {}", idPost, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during pull operation for post ID {}: {}", idPost, e.getMessage(), e);
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }

    /**
     * Push: obtiene el contenido de la nota en HedgeDoc
     * y lo sube al archivo correspondiente en GitHub.
     * Actualiza github_sha y last_synced_at en posts_sync.
     *
     * @param idPost        ID del post a sincronizar
     * @param commitMessage Mensaje del commit en GitHub
     */
    @Override
    @Transactional
    public void push(Integer idPost, String commitMessage) {

        PostSyncEntity postSync = postSyncCrud.findById(idPost)
            .orElseThrow(
                () -> new SyncException(
                    messageSource.getMessage(
                        Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                        new Object[]{idPost},
                        LocaleContextHolder.getLocale()
                    )
                )
            );

        if (postSync.getGithubSha() == null || postSync.getGithubSha().isBlank() ) {
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_SHA_MISSING,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            );
        }

        RepositoryEntity repository = postSync.getRepository();

        try {
            String content = serviceHedgeDoc.getNoteContent(postSync.getHedgedocNoteId() );

            String newSha = serviceGitHub.updateFileContent(
                repository.getOwner(),
                repository.getName(),
                postSync.getFilePath(),
                repository.getDefaultBranch(),
                repository.getGithubToken(),
                content,
                postSync.getGithubSha(),
                commitMessage
            );

            postSync.setGithubSha(newSha);
            postSync.setLastSyncedAt(LocalDateTime.now() );
            postSyncCrud.save(postSync);

            FrontMatter frontMatter = Methods.getFrontMetterToMap(content);
            Set<TagEntity> tagsForNewPost = new HashSet<>();
            if(frontMatter.tags() != null){
                for(String tag : frontMatter.tags() ) {
                    List<TagEntity> listTagsWithSameName = tagsCrud.findByNameIgnoreCase(tag);

                    if(listTagsWithSameName.isEmpty() ) {
                        TagEntity newTag = new TagEntity(null, tag, null);
                        newTag = tagsCrud.save(newTag);
                        tagsForNewPost.add(newTag);
                    } else {
                        tagsForNewPost.add(listTagsWithSameName.get(0) );
                    }
                }
            }

            PostEntity postEntity = postSync.getPost();
            postEntity.setTitle(frontMatter.title() != null ? frontMatter.title() : "Untitled");
            postEntity.setCreatedAt(frontMatter.date() );
            postEntity.setAverageReadDuration(frontMatter.readDuration() != null ? frontMatter.readDuration() : 0);
            postEntity.setDescription(Methods.truncate(frontMatter.description(), 110) );
            postEntity.setLinkImage(frontMatter.image() );
            postEntity.setTags(tagsForNewPost);

            postsCrud.save(postEntity);

            List<PostSyncEntity> postSyncEntities = postSyncCrud.findAllByOrderByPostCreatedAtAsc();

            List<PostEntity> postsForRepository = postSyncEntities.stream()
                .map(PostSyncEntity::getPost)
                .collect(Collectors.toList() );            

            List<RelationPostEntity> relationsForRepository = new ArrayList<>();
            for(int i = 0; i < postsForRepository.size(); i++) {
                PostEntity previousPostEntity = i > 0 ? postsForRepository.get(i - 1) : null;
                PostEntity nextPostEntity = i < postsForRepository.size() - 1 ? postsForRepository.get(i + 1) : null;
                PostEntity originPost = postsForRepository.get(i);
                relationsForRepository.add(
                    new RelationPostEntity(
                        null,
                        originPost,
                        previousPostEntity,
                        nextPostEntity,
                        false
                    )
                );
            }
            
            relationsPostsCrud.deleteByIsInPostList(false);

            relationsPostsCrud.saveAll(relationsForRepository);

        } catch (GitHubException | HedgeDocException e) {
            throw e;
        } catch (Exception e) {
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAllFilesInRepository(Integer idRepository) {
        RepositoryEntity repository = repositoryCrud.findById(idRepository)
            .orElseThrow(
                () -> new SyncException(
                    messageSource.getMessage(
                        Constants.ERR_MSG_SERVICE_SYNC_REPOSITORY_NOT_FOUND,
                        new Object[]{ idRepository },
                        LocaleContextHolder.getLocale()
                    )
                )
            );

        GitTreeResponse response = serviceGitHub.getRepositoryTree(
            repository.getOwner(),
            repository.getName(),
            repository.getDefaultBranch()
        );

        List<String> markdonsFiles = response.tree().stream()
            .filter(item -> item.path().endsWith(".md") )
            .map(it -> it.path() )
            .collect(Collectors.toList() );

        List<PostEntity> allPosts = postsCrud.findAll();

        for (String filePath : markdonsFiles) {
            GitHubFileResponse responseFile = serviceGitHub.getFileAndSha(
                repository.getOwner(),
                repository.getName(),
                filePath,
                repository.getDefaultBranch(),
                repository.getGithubToken()
            );

            if(allPosts.stream().anyMatch(it -> it.getLinkRawMarkdown().equals(responseFile.downloadUrl() ) ) ) {
                log.info("File {} already exists in the database, skipping.", filePath);
                continue;
            }

            FrontMatter frontMatter = Methods.getFrontMetterToMap(responseFile.content() );

            Set<TagEntity> tagsForNewPost = new HashSet<>();
            if(frontMatter.tags() != null){
                for(String tag : frontMatter.tags() ) {
                    List<TagEntity> listTagsWithSameName = tagsCrud.findByNameIgnoreCase(tag);

                    if(listTagsWithSameName.isEmpty() ) {
                        TagEntity newTag = new TagEntity(null, tag, null);
                        newTag = tagsCrud.save(newTag);
                        tagsForNewPost.add(newTag);
                    } else {
                        tagsForNewPost.add(listTagsWithSameName.get(0) );
                    }
                }
            }

            PostEntity postEntity = new PostEntity(
                null,
                frontMatter.title() != null ? frontMatter.title() : "Untitled",
                frontMatter.date(),
                frontMatter.readDuration() != null ? frontMatter.readDuration() : 0,
                Methods.truncate(frontMatter.description(), 110),
                responseFile.downloadUrl() != null ? responseFile.downloadUrl() : new String(),
                frontMatter.image(),
                null,
                false,
                tagsForNewPost,
                null
            );
            postEntity = postsCrud.save(postEntity);

            NoteInfoResponse noteInfoResponse = serviceHedgeDoc.createNote(responseFile.content() );
            String idHedgeDocNote = hedgeDocDataBaseCrud.getNoteId(noteInfoResponse);
            
            PostSyncEntity postSyncEntity = new PostSyncEntity(
                null,
                postEntity,
                repository,
                filePath,
                idHedgeDocNote,
                responseFile.sha(),
                LocalDateTime.now()
            );

            postSyncCrud.save(postSyncEntity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void push(String idNoteHedgeDoc, Integer idRepository, String commitMessage) {
        String note = serviceHedgeDoc.getNoteContent(idNoteHedgeDoc);

        RepositoryEntity repository = repositoryCrud.findById(idRepository)
            .orElseThrow(
                () -> new SyncException(
                    messageSource.getMessage(
                        Constants.ERR_MSG_SERVICE_SYNC_REPOSITORY_NOT_FOUND,
                        new Object[]{ idRepository },
                        LocaleContextHolder.getLocale()
                    )
                )
            );

        try{
            FrontMatter frontMatter = Methods.getFrontMetterToMap(note);

            String path = frontMatter.title();

            if(path == null || path.isBlank() ) {
                path = "Untitled";
            }
            path = path + ".md";

            GitHubCreateResponse shaAndRawUrl = serviceGitHub.createFileContent(
                repository.getOwner(),
                repository.getName(),
                path,
                repository.getDefaultBranch(),
                repository.getGithubToken(),
                note,
                commitMessage
            );

            Set<TagEntity> tagsForNewPost = new HashSet<>();
            if(frontMatter.tags() != null){
                for(String tag : frontMatter.tags() ) {
                    List<TagEntity> listTagsWithSameName = tagsCrud.findByNameIgnoreCase(tag);

                    if(listTagsWithSameName.isEmpty() ) {
                        TagEntity newTag = new TagEntity(null, tag, null);
                        newTag = tagsCrud.save(newTag);
                        tagsForNewPost.add(newTag);
                    } else {
                        tagsForNewPost.add(listTagsWithSameName.get(0) );
                    }
                }
            }

            PostEntity postEntity = new PostEntity(
                null,
                frontMatter.title() != null ? frontMatter.title() : "Untitled",
                frontMatter.date() != null ? frontMatter.date() : LocalDateTime.now(),
                frontMatter.readDuration() != null ? frontMatter.readDuration() : 0,
                Methods.truncate(frontMatter.description(), 110),
                shaAndRawUrl.downloadUrl() != null ? shaAndRawUrl.downloadUrl() : new String(),
                frontMatter.image(),
                null,
                false,
                tagsForNewPost,
                null
            );

            postEntity = postsCrud.save(postEntity);

            PostSyncEntity postSyncEntity = new PostSyncEntity(
                null,
                postEntity,
                repository,
                path,
                idNoteHedgeDoc,
                shaAndRawUrl.sha(),
                LocalDateTime.now()
            );

            postSyncCrud.save(postSyncEntity);

            RelationPostEntity relationPostEntity = new RelationPostEntity();
            relationPostEntity.setIsInPostList(false);
            relationPostEntity.setOriginPost(postEntity);
            
            relationsPostsCrud.save(relationPostEntity);

            List<PostSyncEntity> postSyncEntities = postSyncCrud.findAllByOrderByPostCreatedAtAsc();

            List<PostEntity> postsForRepository = postSyncEntities.stream()
                .map(PostSyncEntity::getPost)
                .collect(Collectors.toList() );            

            List<RelationPostEntity> relationsForRepository = new ArrayList<>();
            for(int i = 0; i < postsForRepository.size(); i++) {
                PostEntity previousPostEntity = i > 0 ? postsForRepository.get(i - 1) : null;
                PostEntity nextPostEntity = i < postsForRepository.size() - 1 ? postsForRepository.get(i + 1) : null;
                PostEntity originPost = postsForRepository.get(i);
                relationsForRepository.add(
                    new RelationPostEntity(
                        null,
                        originPost,
                        previousPostEntity,
                        nextPostEntity,
                        false
                    )
                );
            }
            
            relationsPostsCrud.deleteByIsInPostList(false);

            relationsPostsCrud.saveAll(relationsForRepository);

        } catch (GitHubException | HedgeDocException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error during push operation for HedgeDoc note ID {}: {}", idNoteHedgeDoc, e.getMessage(), e);
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idNoteHedgeDoc},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sharedWithPostiz(Integer idPost, String content) {
        PostEntity postEntity = postsCrud.findById(idPost)
            .orElseThrow(
                () -> new SyncException(
                    messageSource.getMessage(
                        Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                        new Object[]{idPost},
                        LocaleContextHolder.getLocale()
                    )
                )
            );

        String endUrl = Methods.toSlug(postEntity.getTitle() );
        String fullUrl = "www.gerardomarquez.dev/blog/posts/" + endUrl;
        
        try {
            serviceSocialNetwork.toPostOnSocialNetworks(content, fullUrl);
            postEntity.setShared(true);
            postsCrud.save(postEntity);
        } catch (Exception e) {
            log.error("Error during sharing post ID {} to social networks: {}", idPost, e.getMessage(), e);
            throw new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePost(Integer idPost) {

        PostSyncEntity postSyncEntity = postSyncCrud.findByIdPost(idPost).orElseThrow(
            () -> new SyncException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND,
                    new Object[]{idPost},
                    LocaleContextHolder.getLocale()
                )
            )
        );

        RepositoryEntity repositoryEntity = postSyncEntity.getRepository();

        serviceGitHub.deleteFile(
            repositoryEntity.getOwner(),
            repositoryEntity.getName(),
            postSyncEntity.getFilePath(),
            postSyncEntity.getGithubSha(),
            repositoryEntity.getGithubToken()
        ); 
        
        servicePostsCrud.deleteOne(idPost);
    }    
}
