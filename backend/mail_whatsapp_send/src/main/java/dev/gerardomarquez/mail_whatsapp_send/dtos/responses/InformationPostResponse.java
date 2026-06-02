package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import java.util.Set;

/*
 * Record que se regresara el backend para el frontend en cuanto a los datos de los posts de la pagina 
 */
public record InformationPostResponse(
    /**
     * Titulo del post
     */
    String title,

    /**
     * Fecha en la que su publico el post por primera vez
     */
    String date,
    
    /**
     * Duracion promedio en minutos en la que alguien podria acabar de leer el post
     */
    Integer duration,

    /**
     * Descripcion de lo que se va a tratar el post
     */
    String description,

    /**
     * Palabras clave para el articulo del post
     */
    Set<String> tags,

    /**
     * Imagen de previsualizacion del post
     */
    String imageSrc,

    /**
     * Alt que se le pone a la imagen en el front end en la etiqueta <img>
     */
    String imageAlt,

    /**
     * Url del articulo en markdown del repositorio en githubo
     */
    String urlRawMarkDown,

    /**
     * Titulo del post siguiente de acuerdo a la fecha o a la lista de posts en la que esta este (this) post
     */
    String titleNextPost,

    /**
     * Titulo del post anterior de acuerdo a la fecha o a la lista de posts en la que esta este (this) post
     */
    String titlePreviousPost
) {

}
