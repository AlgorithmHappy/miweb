package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

/**
 * Response raiz de la api de Telegram para enviar mensajes desde un bot
 */
public record TelegramResponse(
    Boolean ok,
    TelegramMessageResponse result
) {
}
