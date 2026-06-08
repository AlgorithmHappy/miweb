package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase que reconstruye y despliega la pagina web de internet
 */
@Service
public class ImplementationServiceRebuildAndDeploy implements ServiceRebuildAndDeploy {

    @Autowired
    private WebClient webClient;

    @Value("${vercel.rebuild.deploy.url}")
    private String completedUrs;

    /**
     * {@inheritDoc}
     */
    @Override
    public void rebuildAndDeploy() {
        webClient.post()
            .uri(completedUrs)
            .retrieve()
            .bodyToMono(String.class)
            .block();        
    }

}
