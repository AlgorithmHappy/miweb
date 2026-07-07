package dev.gerardomarquez.mail_whatsapp_send.services;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.gerardomarquez.mail_whatsapp_send.configurations.PostizProperties;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.Post;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.PostizRequest;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.PostizResponse;

/**
 * Clase de implementation que realiza el posteo a cada red social con postiz
 */
@Service
public class ImplementationServiceSocialNetwork implements ServiceSocialNetwork {

    /**
     * Cliente rest para realizar la peticion http
     */
    private final WebClient postizWebClient;

    private final PostizProperties postizProperties;

    ImplementationServiceSocialNetwork(
        @Qualifier("postizWebClient") WebClient postizWebClient,
        PostizProperties postizProperties
    ) {
        this.postizWebClient = postizWebClient;
        this.postizProperties = postizProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toPostOnSocialNetworks(String description, String linkPost) {

        for(Post it: postizProperties.getPosts() ){
            it.value().add(
                new dev.gerardomarquez.mail_whatsapp_send.dtos.requests.Value(
                    description + " " + linkPost,
                    new ArrayList<String>()
                )
            );
        }

        
        String fecha = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now() );

        PostizRequest request = new PostizRequest(
            "now",
            fecha,
            false,
            new ArrayList<String>(),
            postizProperties.getPosts()
        );

        List<PostizResponse> response = postizWebClient
            .post()
            .bodyValue(request)
            .retrieve()
            .bodyToFlux(PostizResponse.class)
            .collectList()
            .block();

    }
}
