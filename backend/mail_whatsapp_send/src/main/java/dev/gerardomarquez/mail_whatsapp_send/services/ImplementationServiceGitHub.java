package dev.gerardomarquez.mail_whatsapp_send.services;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubCreateResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubFileResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitTreeResponse;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

/**
 * Servicio para interactuar con la API REST de GitHub.
 * Permite obtener y actualizar archivos Markdown en repositorios.
 */
@Service
public class ImplementationServiceGitHub implements ServiceGitHub {

    private final WebClient githubWebClient;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;
    private static final Logger log = LoggerFactory.getLogger(ImplementationServiceGitHub.class);

    @Value("${github.api.uri.branch}")
    private String uriBranch;

    @Value("${github.api.uri}")
    private String uri;

    @Value("${github.api.uri.files}")
    private String uriFiles;

    @Value("${github.api.uri.delete}")
    private String uriDelete;

    /**
     * @param githubWebClient WebClient configurado para la API de GitHub
     * @param objectMapper    Mapper para serializar/deserializar JSON
     * @param messageSource   Mensajes del archivo messages.properties
     */
    public ImplementationServiceGitHub(
        @Qualifier("githubWebClient") WebClient githubWebClient,
        ObjectMapper objectMapper,
        MessageSource messageSource
    ) {
        this.githubWebClient = githubWebClient;
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateFileContent(
        String owner, String repo, String filePath, String branch, String token, String content, String sha,
        String commitMessage
    ) {
        try {
            String contentBase64 = Base64.getEncoder().encodeToString(content.getBytes() );

            ObjectNode body = objectMapper.createObjectNode();
            body.put(Constants.JSON_NODE_MESSAGE, commitMessage);
            body.put(Constants.JSON_NODE_CONTENT, contentBase64);
            body.put(Constants.JSON_NODE_SHA, sha);
            body.put(Constants.JSON_NODE_BRANCH, branch);

            // Llamada PUT a la API de GitHub
            String response = githubWebClient.put()
                .uri(uri, owner, repo, filePath)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body.toString() )
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.path(Constants.JSON_NODE_CONTENT).path(Constants.JSON_NODE_SHA).asText();

        } catch (Exception e) {
            log.error("Error al actualizar el contenido del archivo en GitHub: {}", e.getMessage(), e);
            String message = messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_GITHUB_UPDATEFILECONTENT,
                    new Object[]{filePath},
                    Locale.getDefault()
            );
            throw new RuntimeException(message, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitHubFileResponse getFileAndSha(
        String owner,
        String repo,
        String filePath,
        String branch,
        String token
    ) {
        try {
            GitHubFileResponse crudResponse = githubWebClient.get()
                .uri(uriBranch, owner, repo, filePath, branch)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .retrieve()
                .bodyToMono(GitHubFileResponse.class)
                .block();

            return new GitHubFileResponse(
                crudResponse.name(),
                crudResponse.path(),
                crudResponse.sha(),
                crudResponse.size(),
                crudResponse.url(),
                crudResponse.htmlUrl(),
                crudResponse.gitUrl(),
                crudResponse.downloadUrl(),
                crudResponse.type(),
                new String(
                    Base64.getDecoder().decode(
                        crudResponse.content().replace(Constants.LINE_BREAK, new String() )
                    ),
                    StandardCharsets.UTF_8
                ),
                crudResponse.encoding(),
                crudResponse.links()
            );
        } catch (Exception e) {
            log.error("Error al obtener el archivo y su SHA desde GitHub: {}", e.getMessage(), e);
            return new GitHubFileResponse(
                null,
                null,
                null,
                null, 
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitTreeResponse getRepositoryTree(String owner, String name, String branch) {
        try {
            GitTreeResponse repositoryTree = githubWebClient.get()
                .uri(uriFiles, owner, name, branch)
                .retrieve()
                .bodyToMono(GitTreeResponse.class)
                .block();

            return repositoryTree;
        } catch (Exception e) {
            log.error("Error al obtener el árbol de archivos del repositorio en GitHub: {}", e.getMessage(), e);
        }
        
        return new GitTreeResponse(
            null,
            null,
            null,
            null
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitHubCreateResponse createFileContent(
        String owner, String repo, String filePath, String branch, String token, String content, String commitMessage
    ) {
        try {
            String contentBase64 = Base64.getEncoder().encodeToString(content.getBytes() );

            ObjectNode body = objectMapper.createObjectNode();
            body.put(Constants.JSON_NODE_MESSAGE, commitMessage);
            body.put(Constants.JSON_NODE_CONTENT, contentBase64);
            body.put(Constants.JSON_NODE_BRANCH, branch);

            // Llamada PUT a la API de GitHub
            String response = githubWebClient.put()
                .uri(uri, owner, repo, filePath)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body.toString() )
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JsonNode jsonNode = objectMapper.readTree(response);

            String downloadUrl = jsonNode
                .path(Constants.JSON_NODE_CONTENT)
                .path(Constants.JSON_NODE_DOWNLOAD_URL)
                .asText();

            String fileSha = jsonNode
                .path(Constants.JSON_NODE_CONTENT)
                .path(Constants.JSON_NODE_SHA)
                .asText();
            
            return new GitHubCreateResponse(fileSha, downloadUrl);

        } catch (Exception e) {
            log.error("Error al crear el archivo en GitHub: {}", e.getMessage(), e);
            String message = messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_GITHUB_CREATEFILECONTENT,
                    new Object[]{filePath, repo},
                    Locale.getDefault()
            );
            throw new RuntimeException(message, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFile(String owner, String repo, String filePath, String sha, String token) {
        
        ObjectNode body = objectMapper.createObjectNode();
        body.put(
            Constants.JSON_NODE_MESSAGE,
            "Se elimina archivo desde el administrador de la pagina web de gerardomarquez.dev"
        );
        body.put(Constants.JSON_NODE_SHA, sha);

        try {
            // Llamada DELETE a la API de GitHub
            githubWebClient.method(HttpMethod.DELETE)
                .uri(uriDelete, owner, repo, filePath)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        } catch (Exception e) {
            log.error("Error al eliminar el archivo en GitHub: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        
    }

}
