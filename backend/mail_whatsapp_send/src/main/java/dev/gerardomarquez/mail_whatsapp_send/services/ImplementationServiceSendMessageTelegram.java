package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.MessageTelegramRequest;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.TelegramResponse;
import dev.gerardomarquez.mail_whatsapp_send.errors.TelegramApiException;

/**
 * Clase que envia mensajes del bot a mi telegram
 */
@Service
public class ImplementationServiceSendMessageTelegram implements ServiceSendMessage{

    /**
     * Token para poder enviar mensajes del bot al telegram
     */
    @Value("${telegram.api.token}")
    private String telegramApiToken;

    /**
     * Path o Uri del servicio para mandar mensajes por telegram desde el bot
     */
    @Value("${telegram.api.uri}")
    private String telegramApiUri;

    /**
     * Dominio web del servicio para mandar mensajes por telegram desde el bot
     */
    @Value("${telegram.api.domain}")
    private String telegramApiDomain;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(Record requestTelegram) {
        try {
            if(requestTelegram instanceof MessageTelegramRequest request){
                WebClient webClient = WebClient.create(telegramApiDomain);
                String finalUri = String.format( (telegramApiDomain.concat(telegramApiUri) ), telegramApiToken);

                TelegramResponse response = webClient.post()
                    .uri(finalUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(TelegramResponse.class)
                    .block();

                if(response != null && !response.ok() ){
                    throw new TelegramApiException("Error en el consumo de la api de telegram: " + response);
                }
            }
        } catch (TelegramApiException e){
            throw e;
        } catch(Exception e){
            throw new TelegramApiException(e.getMessage(), e);
        }
    }

}
