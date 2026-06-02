package dev.gerardomarquez.mail_whatsapp_send.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.TagEntity;

@Repository
public interface TagsCrud extends JpaRepository<TagEntity, Integer>{
    public List<TagEntity> findByNameContainingIgnoreCase(String name);
}
