package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.ContactFormRequest;

/**
 * Interfaz que define el metodo para insertar los mensajes del formultario de contacto
 * en la base de datos
 */
public interface ServiceMessagesCrud {

    /**
     * Inserta en base de datos el mensaje que se puso en el formulario de contacto de la
     * pagina
     * @param request Request que despues se convertira a entidad para poder ser insertada
     * en la base de datos
     */
    public void insertOne(ContactFormRequest request);
}
