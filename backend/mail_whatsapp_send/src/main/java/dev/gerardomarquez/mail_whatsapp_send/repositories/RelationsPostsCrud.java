package dev.gerardomarquez.mail_whatsapp_send.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;

/**
 * Repositorio que se conecta a la base de datos para las relaciones de una entrada de blog (post)
 */
@Repository
public interface RelationsPostsCrud extends JpaRepository<RelationPostEntity, Integer> {

}
