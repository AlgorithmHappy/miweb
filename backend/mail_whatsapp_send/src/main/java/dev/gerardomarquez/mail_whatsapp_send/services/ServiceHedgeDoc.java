package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Contrato del servicio para interactuar con la API de HedgeDoc.
 * Solo lectura, ya que HedgeDoc es el editor principal y
 * GitHub es el respaldo.
 */
public interface ServiceHedgeDoc {

    /**
     * Obtiene el contenido Markdown de una nota de HedgeDoc.
     *
     * @param noteId ID de la nota en HedgeDoc
     * @return Contenido de la nota en texto plano (Markdown)
     */
    String getNoteContent(String noteId);

    /**
     * Crea una nota nueva en HedgeDoc con el contenido proporcionado.
     * Se usa únicamente al configurar un par nuevo (carga inicial desde GitHub).
     * Es decir no actualiza la crea, no se puede actualizar
     *
     * @param content Contenido Markdown en texto plano
     * @return ID de la nota recién creada en HedgeDoc
     */
    String createNote(String content);
}
