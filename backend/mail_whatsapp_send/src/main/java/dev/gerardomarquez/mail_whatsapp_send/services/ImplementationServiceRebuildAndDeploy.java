package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase que reconstruye y despliega la pagina web de internet
 */
@Service
public class ImplementationServiceRebuildAndDeploy implements ServiceRebuildAndDeploy {

    private final WebClient webClient;

    ImplementationServiceRebuildAndDeploy(
        @Qualifier("vercelWebClient") WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rebuildAndDeploy() {
        webClient.post()
            .retrieve()
            .bodyToMono(String.class)
            .block();        
    }

}
