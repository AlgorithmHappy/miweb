package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase de implementation que realiza el posteo a cada red social con postiz
 */
@Service
public class ImplementationServiceSocialNetwork implements ServiceSocialNetwork {

    @Autowired
    private WebClient postizWebClient;

    @Value("${postiz.integration.facebook}")
    private String facebookIntegrationId;

    @Value("${postiz.integration.linkedin}")
    private String linkedinIntegrationId;

    @Value("${postiz.integration.x}")
    private String xIntegrationId;

    @Value("${postiz.integration.instagram}")
    private String instagramIntegrationId;

    @Value("${postiz.integration.telegram}")
    private String telegramIntegrationId;

    @Value("${postiz.integration.whatsapp}")
    private String whatsappIntegrationId;

    @Value("${postiz.integration.discord}")
    private String discordIntegrationId;
}
