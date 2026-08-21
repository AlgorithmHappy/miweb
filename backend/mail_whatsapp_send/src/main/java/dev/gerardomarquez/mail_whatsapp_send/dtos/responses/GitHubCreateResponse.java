package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

/**
 * DTO de respuesta para la creación de un archivo en GitHub.
 * @param sha           SHA del archivo recién creado en GitHub
 * @param downloadUrl   URL de descarga del archivo recién creado en GitHub
 */
public record GitHubCreateResponse(
    String sha,
    String downloadUrl
) {
}
