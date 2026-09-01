package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Contrato del servicio orquestador de sincronización
 * entre HedgeDoc y GitHub.
 */
public interface ServiceSync {
    
    /**
     * Pull: obtiene el contenido de un archivo en GitHub
     * y crea una nota nueva en HedgeDoc con ese contenido.
     * Actualiza el hedgedoc_note_id y el github_sha en posts_sync.
     *
     * @param idPost ID del post a sincronizar
     * @return ID de la nota recién creada en HedgeDoc
     */
    public String pull(Integer idPost);

    /**
     * Push: obtiene el contenido de la nota en HedgeDoc
     * y lo sube al archivo correspondiente en GitHub.
     * Actualiza el github_sha y last_synced_at en posts_sync.
     *
     * @param idPost        ID del post a sincronizar
     * @param commitMessage Mensaje del commit en GitHub
     */
    public void push(Integer idPost, String commitMessage);

    /**
     * Actualiza todos los archivos de un repositorio en GitHub
     * @param idRepository ID del repositorio a actualizar
     */
    public void updateAllFilesInRepository(Integer idRepository);

    /**
     * Push: obtiene el contenido de la nota en HedgeDoc
     * y lo sube al archivo correspondiente en GitHub.
     * @param idNoteHedgeDoc ID de la nota en HedgeDoc a sincronizar
     * @param idRepository   ID del repositorio en GitHub
     * @param commitMessage  Mensaje del commit en GitHub
     */
    public void push(String idNoteHedgeDoc, Integer idRepository, String commitMessage);

    
    /**
     * Comparte la url del post en las redes sociales configuradas en Postiz.
     * @param idPost ID del post a compartir en las redes sociales
     * @param content Contenido de la publicacion para que den clic en el link
     */
    public void sharedWithPostiz(Integer idPost, String content);

    /**
     * Elimina un post de gitHub y de la base de datos de sincronización pero no de hedgedoc
     * @param idPost ID del post a eliminar de GitHub y de la base de datos de sincronización
     */
    public void deletePost(Integer idPost);
}
