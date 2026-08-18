package dev.gerardomarquez.mail_whatsapp_send.services;

import java.util.List;

import dev.gerardomarquez.mail_whatsapp_send.dtos.RepositorySection;

/**
 * Interfaz para el servicio de presentación de la lista de notas para el frontend en
 * la pagina donde se muestan todas las notas.
 */
public interface ServicePresentationIndex {

    /**
     * Obtiene una lista de filas de notas de HeadgeDoc para publicarlas en github y en la pagina.
     * @return Lista de repositorios con las filas de notas de HeadgeDoc para publicarlas en github y en la pagina.
     */
    public List<RepositorySection> getRowsNotesForPublicPosts();    
}
