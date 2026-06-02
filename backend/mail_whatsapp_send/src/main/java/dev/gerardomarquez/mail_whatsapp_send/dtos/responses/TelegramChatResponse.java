package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response de telegram para el atributo: chat
 */
public record TelegramChatResponse(
    Long id,
    @JsonProperty("first_name")
    String first_name,
    @JsonProperty("username")
    String userName,
    String type
) {
}
