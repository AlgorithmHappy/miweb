package dev.gerardomarquez.mail_whatsapp_send.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("vercelWebClient")
    public WebClient vercelWebClient(
        @Value("${vercel.rebuild.deploy.url}") String completedUrl
    ) {
        return WebClient.builder().baseUrl(completedUrl).build();
    }

    @Bean("postizWebClient")
    public WebClient postizWebClient(
        @Value("${postiz.base.url}") String baseUrl,
        @Value("${postiz.api.key}") String apiKey
    ) {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", apiKey)
            .build();
    }

    @Bean("telegramWebClient")
    public WebClient telegramWebClient(
        @Value("${telegram.api.domain}") String telegramApiDomain,
        @Value("${telegram.api.uri}") String telegramApiUri,
        @Value("${telegram.api.token}") String telegramApiToken
    ) {
        return WebClient.builder()
            .baseUrl(String.format( (telegramApiDomain.concat(telegramApiUri) ), telegramApiToken) )
            .build();
    }

    /**
     * WebClient configurado para la API de GitHub.
     * La URL base y headers comunes se configuran aquí
     * para no repetirlos en cada llamada.
     *
     * @param githubApiUrl URL base de la API de GitHub
     * @return WebClient listo para llamar a GitHub
     */
    @Bean(name = "githubWebClient")
    public WebClient githubWebClient(
        @Value("${github.api.url}") String githubApiUrl
    ) {
        return WebClient.builder()
            .baseUrl(githubApiUrl)
            .defaultHeader("Accept", "application/vnd.github+json")
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            .build();
    }

    /**
     * WebClient configurado para la API de HedgeDoc.
     *
     * @param hedgedocUrl URL base de HedgeDoc
     * @return RestClient listo para llamar a HedgeDoc
     */
    @Bean(name = "hedgedocWebClient")
    public RestClient hedgedocWebClient(
        @Value("${hedgedoc.url}") String hedgedocUrl
    ) {
        return RestClient.builder()
            .baseUrl(hedgedocUrl)
            .requestFactory(new SimpleClientHttpRequestFactory() )
            .build();
    }
}
