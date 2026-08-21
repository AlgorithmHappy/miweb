package dev.gerardomarquez.mail_whatsapp_send.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import dev.gerardomarquez.mail_whatsapp_send.entities.RepositoryEntity;

@Repository
public interface RepositoryCrud extends JpaRepository<RepositoryEntity, Integer> {
    
    Optional<RepositoryEntity> findByOwnerAndName(
        String owner,
        String name
    );
}
