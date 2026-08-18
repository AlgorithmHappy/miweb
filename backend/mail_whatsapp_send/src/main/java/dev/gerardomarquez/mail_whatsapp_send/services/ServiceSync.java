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
}
