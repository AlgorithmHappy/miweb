package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

@Service
public class ServiceImplementationHedgeDoc implements ServiceHedgeDoc {
    
    private final WebClient hedgedocWebClient;
    private final MessageSource messageSource;

    @Value("${hedgedoc.url.download}")
    private String urlDownload;

    @Value("${hedgedoc.url.new}")            
    private String urlNew;

    
    public ServiceImplementationHedgeDoc(
        @Qualifier("hedgedocWebClient") WebClient hedgedocWebClient,
        MessageSource messageSource
    ) {
        this.hedgedocWebClient = hedgedocWebClient;
        this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNoteContent(String noteId) {
        try {
            return hedgedocWebClient.get()
                .uri(urlDownload, noteId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        } catch (Exception e) {
            throw new RuntimeException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_HEDGEDOC_GETNOTECONTENT,
                    new Object[]{noteId},
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createNote(String content) {
        try {
            return hedgedocWebClient.post()
                    .uri(urlNew)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_MARKDOWN_VALUE)
                    .bodyValue(content)
                    .exchangeToMono(response -> {
                        String location = response.headers()
                                .asHttpHeaders()
                                .getFirst(Constants.HEADER_LOCATION);
                        if (location != null) {
                            return reactor.core.publisher.Mono.just(
                                    location.replace(Constants.SLASH, new String() ) );
                        }
                        return response.bodyToMono(String.class);
                    })
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(
                messageSource.getMessage(
                    Constants.ERR_MSG_SERVICE_HEDGEDOC_CREATENOTE,
                    null,
                    LocaleContextHolder.getLocale()
                ),
                e
            );
        }
    }
}
