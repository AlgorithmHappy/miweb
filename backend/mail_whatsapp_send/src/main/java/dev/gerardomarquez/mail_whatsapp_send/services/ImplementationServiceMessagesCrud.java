package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.ContactFormRequest;
import dev.gerardomarquez.mail_whatsapp_send.repositories.MessagesCrud;

/**
 * Clase que implementa el metodo para insertar los mensajes del formultario de contacto
 * en la base de datos
 */
@Service
public class ImplementationServiceMessagesCrud implements ServiceMessagesCrud {

    /**
     * Repositorio para insertar el mensaje en base de datos
     */
    @Autowired
    private MessagesCrud messagesCrud;

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertOne(ContactFormRequest request) {
        messagesCrud.save(request.toEntity() );
    }

}
