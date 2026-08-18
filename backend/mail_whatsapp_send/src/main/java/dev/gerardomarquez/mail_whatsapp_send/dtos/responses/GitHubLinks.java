package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

/**
 * Objeto interno del response de github al obtener el archivo con un pull
 * GitHubLinks
 * @param self
 * @param git
 * @param html
 */
public record GitHubLinks(
    String self,
    String git,
    String html
) {
}
