package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Interfaz que define las peticiones de los servicios rest para cada red socioal en
 * postiz
 */
public interface ServiceSocialNetwork {

    /**
     * Metodo que realiza una peticion rest a postiz para postear la entrada de blog en
     * Facebook, linkedin, X, telegram y Discord
     * @param description Descripcion que se pondra en la publicacion de facebook,
     * linkedin, X, telegram y Discord para que alente a la gente a ir al post
     * @param linkPost Link que se adjuntara a la publicacion de facebook, linkedin, X,
     * telegram y Discord
     */
    public void toPostOnSocialNetworks(String description, String linkPost);

}
