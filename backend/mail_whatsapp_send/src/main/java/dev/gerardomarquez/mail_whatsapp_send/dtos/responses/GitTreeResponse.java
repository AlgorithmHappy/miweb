package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import java.util.List;

/**
 * DTO que representa la respuesta de la API de GitHub al obtener el árbol de archivos
 * de un repositorio.
 * @param sha SHA del commit o árbol consultado
 * @param url URL del recurso en la API de GitHub
 * @param tree Lista de items del árbol de archivos (archivos y directorios)
 * @param truncated Indica si el árbol fue truncado (true) o no (false). Si es true,
 * significa que el árbol es demasiado grande y no se devolvió completo.
 */
public record GitTreeResponse(
        String sha,
        String url,
        List<GitTreeItem> tree,
        Boolean truncated
) {
}