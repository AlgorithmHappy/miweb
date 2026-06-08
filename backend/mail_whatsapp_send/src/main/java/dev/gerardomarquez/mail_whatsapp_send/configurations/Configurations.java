package dev.gerardomarquez.mail_whatsapp_send.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Configurations {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public WebClient postizWebClient(
        @Value("${postiz.base.url}") String baseUrl,
        @Value("${postiz.api.key}") String apiKey
    ) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", apiKey)
                .build();
    }
}
