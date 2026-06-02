package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response de telegram para el atributo: from
 */
public record TelegramFromResponse(
    Long id,
    @JsonProperty("is_bot")
    Boolean isBot,
    @JsonProperty("first_name")
    String firstName,
    @JsonProperty("username")
    String userName
) {
}
