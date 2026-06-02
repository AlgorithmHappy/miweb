package dev.gerardomarquez.mail_whatsapp_send.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;

/**
 * Repositorio que se conecta a la base de datos para las entradas de blog
 */
@Repository
public interface PostsCrud extends JpaRepository<PostEntity, Integer> {

    Page<PostEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<PostEntity> findDistinctByTags_IdIn(List<Integer> tagIds, Pageable pageable);

    Page<PostEntity> findDistinctByTitleContainingIgnoreCaseAndTags_IdIn(String title, List<Integer> tags, Pageable pageable);

    Optional<PostEntity> findFirstByTitle(String title);
}
