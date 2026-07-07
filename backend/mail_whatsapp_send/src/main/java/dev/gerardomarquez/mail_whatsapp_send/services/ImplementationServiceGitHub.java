package dev.gerardomarquez.mail_whatsapp_send.services;

import java.util.Base64;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    @Value("${github.api.uri.branch}")
    private String uriBranch;

    @Value("${github.api.uri}")
    private String uri;

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
    public String getFileContent(String owner, String repo, String filePath, String branch, String token) {
        try {
            String response = githubWebClient.get()
                .uri(uriBranch, owner, repo, filePath, branch)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JsonNode jsonNode = objectMapper.readTree(response);

            String contentBase64 = jsonNode.get(Constants.JSON_NODE_CONTENT).asText().replace(Constants.LINE_BREAK, new String() );

            return new String(Base64.getDecoder().decode(contentBase64) );

        } catch (Exception e) {            
            String message = messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_GITHUB_GETFILECONTENT,
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
    public String getFileSha(String owner, String repo, String filePath, String branch, String token) {
        try {
            String response = githubWebClient.get()
                .uri(uriBranch, owner, repo, filePath, branch)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get(Constants.JSON_NODE_SHA).asText();

        } catch (Exception e) {
            String message = messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_GITHUB_GETFILESHA,
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
    public String updateFileContent(
        String owner, String repo, String filePath, String branch, String token, String content, String sha,
        String commitMessage
    ) {
        try {
            String contentBase64 = Base64.getEncoder().encodeToString(content.getBytes());

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
            String message = messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_GITHUB_UPDATEFILECONTENT,
                    new Object[]{filePath},
                    Locale.getDefault()
            );
            throw new RuntimeException(message, e);
        }
    }

}
