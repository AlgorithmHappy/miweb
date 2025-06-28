package dev.gerardomarquez.mail_whatsapp_send.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;
import dev.gerardomarquez.mail_whatsapp_send.repositories.MessagesCrud;

/*
 * Clase de implementacion
 * {@inheritDoc}
 */
@Service
public class ImplementationServiceMessagesCrud implements ServiceMessagesCrud {

    @Autowired
    private MessagesCrud messagesCrud;

    /*
     * {@inheritDoc}
     */
    @Override
    public void insertOne(ContactMessage contactMessage) {
        messagesCrud.save(contactMessage.toEntity() );
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public List<ContactMessage> findAll() {
        return messagesCrud.findAll().stream().map(
            (it) -> {
                return ContactMessage.entityToContactMessage(it);
            }
        ).collect(Collectors.toList() );
    }

}
