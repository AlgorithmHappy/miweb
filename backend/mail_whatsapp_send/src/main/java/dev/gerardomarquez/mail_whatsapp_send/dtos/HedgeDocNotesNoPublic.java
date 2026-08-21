package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.time.LocalDateTime;

/**
 * DTO que representa la información de una nota en HedgeDoc que no es pública en github.
 */
public record HedgeDocNotesNoPublic(
    String shortId,
    String title,
    LocalDateTime updatedAt,
    String permission,
    Boolean isEnabled
) {

}
