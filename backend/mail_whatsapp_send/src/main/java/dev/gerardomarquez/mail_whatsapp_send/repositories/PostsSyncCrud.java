package dev.gerardomarquez.mail_whatsapp_send.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.PostSyncEntity;

@Repository
public interface PostsSyncCrud extends JpaRepository<PostSyncEntity, Integer>{

}
