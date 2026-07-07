package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Qualifier;

import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.MessageTelegramRequest;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.TelegramResponse;
import dev.gerardomarquez.mail_whatsapp_send.errors.TelegramApiException;

/**
 * Clase que envia mensajes del bot a mi telegram
 */
@Service
public class ImplementationServiceSendMessageTelegram implements ServiceSendMessage{

    private final WebClient webClient;

    ImplementationServiceSendMessageTelegram(
        @Qualifier("telegramWebClient") WebClient webClient
    ) {
        this.webClient = webClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(Record requestTelegram) {
        try {
            if(requestTelegram instanceof MessageTelegramRequest request){
                TelegramResponse response = webClient.post()
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
