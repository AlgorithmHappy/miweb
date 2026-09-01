package dev.gerardomarquez.mail_whatsapp_send.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.PostSyncEntity;

/**
 * Repositorio para la entidad posts_sync.
 */
@Repository
public interface PostsSyncCrud extends JpaRepository<PostSyncEntity, Integer>{

    /**
     * Devuelve una entidad PostSyncEntity por su idPost envuelta en un Optional.
     * @param idPost El ID del post que se desea buscar.
     * @return Un Optional que contiene la entidad PostSyncEntity si se encuentra, o vacío si no existe.
     */
    public Optional<PostSyncEntity> findByIdPost(Integer idPost);

    /**
     * Devuelve todas las entidades PostSyncEntity ordenadas por la fecha de creación del post
     * en orden ascendente.
     * @return Una lista de entidades PostSyncEntity ordenadas por postCreatedAt de manera ascendente.
     */
    public List<PostSyncEntity> findAllByOrderByPostCreatedAtAsc();
}
