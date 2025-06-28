package dev.gerardomarquez.mail_whatsapp_send.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.MessagesFromContactForm;

/*
 * Repositorio de spring data que hace el crud para la base de datos
 */
@Repository
public interface MessagesCrud extends JpaRepository<MessagesFromContactForm, UUID> {

}
