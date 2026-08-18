package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitHubFileResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.GitTreeResponse;

/**
 * Servicio para interactuar con la API REST de GitHub.
 * Permite obtener y actualizar archivos Markdown en repositorios.
 */
public interface ServiceGitHub {

    /**
     * Obtiene el SHA y el archivo markdown actual en GitHub.
     * El SHA es requerido por la API de GitHub para actualizar un archivo
     * sin generar un conflicto 409.
     * @param owner    Usuario u organización dueña del repo
     * @param repo     Nombre del repositorio
     * @param filePath Ruta del archivo dentro del repo
     * @param branch   Rama a consultar
     * @param token    Personal Access Token de GitHub
     * @return         Objeto con el SHA y el contenido del archivo en texto plano (Markdown),
     * este es el response de GitHub al hacer pull
     */
    public GitHubFileResponse getFileAndSha(
        String owner,
        String repo,
        String filePath,
        String branch,
        String token
    );

    /**
     * Actualiza el contenido de un archivo Markdown en GitHub.
     * Internamente codifica el contenido a Base64 y hace un PUT
     * a la API de GitHub incluyendo el SHA actual del archivo.
     *
     * @param owner         Usuario u organización dueña del repo
     * @param repo          Nombre del repositorio
     * @param filePath      Ruta del archivo dentro del repo
     * @param branch        Rama donde se hará el push
     * @param token         Personal Access Token de GitHub
     * @param content       Contenido Markdown en texto plano
     * @param sha           SHA actual del archivo (obtenido con getFileSha)
     * @param commitMessage Mensaje del commit
     * @return              SHA nuevo del archivo tras el push
     */
    public String updateFileContent(
        String owner,
        String repo,
        String filePath,
        String branch,
        String token,
        String content,
        String sha,
        String commitMessage
    );

    /**
     * Obtiene el árbol de archivos de un repositorio en GitHub mediante
     * una peticion rest full a la api de github.
     * @param owner
     * @param name
     * @param branch
     * @return
     */
    public GitTreeResponse getRepositoryTree(
        String owner, String name, String branch
    );
}
