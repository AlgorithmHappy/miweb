package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Servicio para interactuar con la API REST de GitHub.
 * Permite obtener y actualizar archivos Markdown en repositorios.
 */
public interface ServiceGitHub {

    /**
     * Obtiene el contenido de un archivo Markdown de un repositorio de GitHub.
     * El contenido viene en Base64 desde la API, este método lo decodifica
     * y devuelve el texto plano.
     *
     * @param owner      Usuario u organización dueña del repo (ej. "AlgorithmHappy")
     * @param repo       Nombre del repositorio (ej. "curso-java")
     * @param filePath   Ruta del archivo dentro del repo (ej. "docs/intro.md")
     * @param branch     Rama a consultar (ej. "main")
     * @param token      Personal Access Token de GitHub con scope "repo"
     * @return           Contenido del archivo en texto plano (Markdown)
     */
    public String getFileContent(String owner, String repo, String filePath, String branch, String token);

    /**
     * Obtiene el SHA actual de un archivo en GitHub.
     * El SHA es requerido por la API de GitHub para actualizar un archivo
     * sin generar un conflicto 409.
     *
     * @param owner    Usuario u organización dueña del repo
     * @param repo     Nombre del repositorio
     * @param filePath Ruta del archivo dentro del repo
     * @param branch   Rama a consultar
     * @param token    Personal Access Token de GitHub
     * @return         SHA actual del archivo
     */
    public String getFileSha(String owner, String repo, String filePath, String branch, String token);

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
        String owner, String repo, String filePath, String branch, String token, String content, String sha,
        String commitMessage
    );
}
