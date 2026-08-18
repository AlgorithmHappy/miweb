package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.time.LocalDateTime;

/**
 * DTO para representar una fila de notas de HeadgeDoc para publicarlas en github
 * y en la pagina.
 */
public record RowNotesForPublicPosts(
    Integer idPost,
    String hedgeDocNoteId,
    String title,
    String gitHubFilePath,
    LocalDateTime lastSyncedAt,
    Boolean enabledNote
) {

}
