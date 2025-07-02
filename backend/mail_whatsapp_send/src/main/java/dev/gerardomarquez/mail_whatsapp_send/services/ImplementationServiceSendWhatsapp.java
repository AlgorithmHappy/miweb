package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;
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

    @Value("${evolution.api.message}")
    private String evolutionApiMessage;

    @Value("${evolution.api.send.phone.number}")
    private String evolutionApiNumber;

    @Value("${evolution.api.endpoint.key.header}")
    private String evolutionApiKeyHeader;

    /*
     * {@inheritDoc}
     */
    @Override
    public void sendWhatsappMessage(ContactMessage contactMessage) {
        WebClient webClient = WebClient.create(evolutionApiUrl);
        SendWhatsapp sendWhatsapp = new SendWhatsapp(
            evolutionApiNumber,
            String.format(
                evolutionApiMessage,
                contactMessage.getFullName(),
                contactMessage.getEmail(),
                contactMessage.getMessage()
            )  
        );

        webClient.post()
            .uri(evolutionApiUri)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(sendWhatsapp)
            .header(evolutionApiKeyHeader, evolutionApiKey)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

}
