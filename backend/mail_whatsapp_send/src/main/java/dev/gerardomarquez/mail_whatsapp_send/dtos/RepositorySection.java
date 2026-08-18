package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.util.List;

/**
 * DTO para representar una sección en el frontend para desplegarlo como acordeón
 * @param owner Propietario del repositorio
 * @param name Nombre del repositorio
 * @param branch Rama del repositorio
 */
public record RepositorySection(
    Integer id,
    String owner,
    String name,
    String branch,
    List<RowNotesForPublicPosts> rowsNotesForPublicPosts
) {

}
