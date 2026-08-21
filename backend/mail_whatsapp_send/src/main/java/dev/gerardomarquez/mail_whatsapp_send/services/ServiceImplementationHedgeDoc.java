package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.NoteInfoResponse;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

@Service
public class ServiceImplementationHedgeDoc implements ServiceHedgeDoc {
    
    private final RestClient hedgedocWebClient;
    private final MessageSource messageSource;

    @Value("${hedgedoc.url.download}")
    private String urlDownload;

    @Value("${hedgedoc.url.new}")            
    private String urlNew;

    @Value("${hedgedoc.url.info}")
    private String urlInfo;

    
    public ServiceImplementationHedgeDoc(
        @Qualifier("hedgedocWebClient") RestClient hedgedocWebClient,
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
                .body(String.class);
        } catch (Exception e) {
            System.out.println("Error al obtener el contenido de la nota: " + e.getMessage() );
            e.printStackTrace();
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
    public NoteInfoResponse createNote(String content) {
        try {
            ResponseEntity<String> response = hedgedocWebClient.post()
                .uri(urlNew)
                .header(HttpHeaders.CONTENT_TYPE, "text/markdown;charset=UTF-8")
                .body(content)
                .retrieve()
                .toEntity(String.class);
            String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);

            String id = location.substring(location.lastIndexOf('/') + 1);

            NoteInfoResponse noteInforResponse = hedgedocWebClient.get()
                .uri(urlInfo, id)
                .retrieve()
                .body(NoteInfoResponse.class);

            return noteInforResponse;
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
