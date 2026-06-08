package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.data.domain.Pageable;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.InformationPostResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.PageResponse;
import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;
import dev.gerardomarquez.mail_whatsapp_send.entities.RelationPostEntity;

/**
 * Interfaz de spring "service" para conectarse al repositorio para consultar, actualizar, insertar y
 * borrar registros de la base de datos de las entradas del blog
 */
public interface ServicePostsCrud {

    /**
     * Metodo que devuelve la informacion de base de datos de acuerdo a la pgina, titulo y tag pasados
     * como argumento
     * @param pageable Parametros del paginado, tamaño, numero de pagina, etc.
     * @param tag Etiquetas (palabras clave) si se requiere buscar algo en especifico
     * @param title Titulo si se requiere buscar algo en especifico
     * @return Response de una pagina con la lista de renglones encontrados en base de datos
     */
    public ApiResponse<PageResponse<InformationPostResponse> > findAllByPageAndTitleAndTag(
        Pageable pageable,
        String tag,
        String title
    );

    /**
     * Metodo que inserta un registro de post (entrada de blog) en la base de datos, a partir de los
     * datos de github
     * @param owner Usuario quien creo el repositorio de github
     * @param repo Nombre del repositorio de github
     * @param branch Rama del repositorio de github
     * @param filePath Directorio del archivo markdown en el repositorio
     * @return Entidad que se creo
     */
    public PostEntity insertOnePost(String owner, String repo, String branch, String filePath);

    /**
     * Metodo que elimina el post de la base de datos con el filePath que le entrega github
     * @param filePath Directorio del archivo markdown en el repositorio
     */
    public void deleteOne(String filePath);

    /**
     * Metodo que devuelve el post (entrada de blog)
     * @param filePath Directorio del archivo markdown en el repositorio
     * @param Post Entidad del post (entrada del blog) del repositorio de la base de datos.
     */
    public PostEntity findOnePostByFilePath(String filePath);

    /**
     * Metodo que inserta o actualiza en base de datos una relacion de un post
     * @param relationPost Entidad de la relacion del post del repositorio
     */
    public void insertOrUpdateOneRelationPost(RelationPostEntity relationPost);
    
    /**
     * Metodo que borra una relacion de un post
     * @param relationPost Entidad de la relacion del post del repositorio a eliminar
     */
    public void deleteOneRelationPost(RelationPostEntity relationPost);

    /**
     * Metodo que busca el ultimo post (entrada de blog) por fecha en la base de datos
     */
    public PostEntity findLastPost();
}
