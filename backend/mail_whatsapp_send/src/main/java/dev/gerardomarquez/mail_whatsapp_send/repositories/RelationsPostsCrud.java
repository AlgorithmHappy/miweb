package dev.gerardomarquez.mail_whatsapp_send.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;

/**
 * Repositorio que se conecta a la base de datos para las relaciones de una entrada de blog (post)
 */
@Repository
public interface RelationsPostsCrud extends JpaRepository<RelationPostEntity, Integer> {

    /**
     * Devuelve una lista de entidades RelationPostEntity por el ID del post ancla especificado.
     * @param idPost El ID del post ancla (post de enmedio) para el cual se desean obtener las relaciones.
     * @return Una lista de entidades RelationPostEntity la cual contiene el post anterior y posterior
     * con referencia al ancla.
     */
    public List<RelationPostEntity> findByOriginPostId(Integer idPost);

    /**
     * Elimina todas las relaciones de posts según el valor de la bandera isInPostList.
     * @param isInPostList El valor de la bandera isInPostList que se utilizará para filtrar las
     * relaciones a eliminar.
     */
    public void deleteByIsInPostList(Boolean isInPostList);
}
