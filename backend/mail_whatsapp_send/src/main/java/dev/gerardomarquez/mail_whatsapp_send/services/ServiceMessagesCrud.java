package dev.gerardomarquez.mail_whatsapp_send.services;

import java.util.List;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;

/*
 * Interface que define el crud para insertar a la base de datos los usuarios que me han
 * contactado
 */
public interface ServiceMessagesCrud {
    /*
     * Inserta un usuario con su mensaje
     * @param contactMessage El mensaje del contacto con el nombre y el correo
     */
    public void insertOne(ContactMessage contactMessage);
    /*
     * Devuelve todos los usuarios que me han contactado
     * @return Lista de todos los mensajes de contacto recibidos
     */
    public List<ContactMessage> findAll();
}
