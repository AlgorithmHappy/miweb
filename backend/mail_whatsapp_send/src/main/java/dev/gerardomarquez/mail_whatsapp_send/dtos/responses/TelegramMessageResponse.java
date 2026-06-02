package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Datos del response de TelegramResponse
 */
public record TelegramMessageResponse(
    @JsonProperty("message_id")
    Integer messageId,
    TelegramFromResponse from,
    TelegramChatResponse chat,
    Long date,
    String text
) {
    
}
