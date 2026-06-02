package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request que tiene que recivir telegram para poder enviar el mensaje
 */
public record MessageTelegramRequest(
    /**
     * Id del chat donde se recivira el mensaje
     */
    @JsonProperty("chat_id")
    Long chatId,

    /**
     * Mensaje a enviar
     */
    String text
) {

}
