package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.NoteInfoResponse;

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
    public String getNoteContent(String noteId);

    /**
     * Crea una nota nueva en HedgeDoc con el contenido proporcionado.
     * Se usa únicamente al configurar un par nuevo (carga inicial desde GitHub).
     * Es decir no actualiza la crea, no se puede actualizar
     *
     * @param content Contenido Markdown en texto plano
     * @return Informacion de hedgeNote de la nota creada
     */
    public NoteInfoResponse createNote(String content);
}
