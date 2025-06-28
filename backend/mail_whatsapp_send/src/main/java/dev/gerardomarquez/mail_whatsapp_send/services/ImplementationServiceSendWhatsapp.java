package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.gerardomarquez.mail_whatsapp_send.dtos.SendWhatsapp;

/*
 * Implementacion
 * {@inheritDoc}
 */
@Service
public class ImplementationServiceSendWhatsapp implements ServiceSendWhatsapp{

    @Value("${evolution.api.endpoint.ip}")
    private String evolutionApiUrl;

    @Value("${evolution.api.endpoint.uri}")
    private String evolutionApiUri;

    @Value("${evolution.api.endpoint.key}")
    private String evolutionApiKey;

    /*
     * {@inheritDoc}
     */
    @Override
    public void sendWhatsappMessage(SendWhatsapp sendWhatsapp) {
        WebClient webClient = WebClient.create(evolutionApiUrl);

        webClient.post()
            .uri(evolutionApiUri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(sendWhatsapp)
            .header("apikey", evolutionApiKey)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
