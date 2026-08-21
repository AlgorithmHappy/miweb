package dev.gerardomarquez.mail_whatsapp_send.dtos;

/**
 * DTO para representar una nota de hedgeDoc protegida o no a la que se le puede hacer push o pull en github.
 */
public record NoteEnabled(
    String noteId,
    Boolean enabled,
    String permission
) {

}
