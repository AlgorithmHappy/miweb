package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

/**
 * DTO que representa un item del árbol de archivos
 * de un repositorio del response de GitHub.
 * @param path Ruta del archivo dentro del repositorio
 * @param mode Permisos del archivo (ej. 100644)
 * @param type Tipo de objeto (ej. blob, tree)
 * @param sha SHA del objeto
 * @param size Tamaño del archivo en bytes (null si es un directorio)
 * @param url URL del objeto en la API de GitHub
 */
public record GitTreeItem(
        String path,
        String mode,
        GitObjectType type,
        String sha,
        Integer size,
        String url
) {
}