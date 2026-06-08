package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Interfaz que define las peticiones de los servicios rest para cada red socioal en
 * postiz
 */
public interface ServiceSocialNetwork {

    /**
     * Metodo que realiza una peticion rest a postiz para postear la entrada de blog en
     * Facebook
     * @param description Descripcion que se pondra en la publicacion de facebook para que
     * alente a la gente a ir al post
     * @param linkPost Link que se adjuntara a la publicacion de facebook
     */
    public void toPostOnFacebook(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para postear la entrada de blog en
     * Linkedin
     * @param description Descripcion que se pondra en la publicacion de linkedin para que
     * alente a la gente a ir al post
     * @param linkPost Link que se adjuntara a la publicacion de linkedin
     */
    public void toPostOnLinkedin(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para postear la entrada de blog en
     * X
     * @param description Descripcion que se pondra en la publicacion de X para que alente a
     * la gente a ir al post
     * @param linkPost Link que se adjuntara a la publicacion de X
     */
    public void toPostOnX(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para postear la entrada de blog en
     * Instagram
     * @param description Descripcion que se pondra en la publicacion de Instagram para que
     * alente a la gente a ir al post
     * @param linkPost Link que se adjuntara a la publicacion de Instagram
     */
    public void toPostOnInstagram(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para mandar mensaje de la entrada de blog
     * en el canal de Telegram
     * @param description Descripcion que se pondra en el mensaje de Telegram para que alente a la
     * gente a ir al post
     * @param linkPost Link que se adjuntara al mensaje de Telegram
     */
    public void toPostOnTelegram(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para mandar mensaje de la entrada de blog
     * en el canal de Whatsapp
     * @param description Descripcion que se pondra en el mensaje de Whatsapp para que alente a la
     * gente a ir al post
     * @param linkPost Link que se adjuntara al mensaje de Whatsapp
     */
    public void toPostOnWhatsapp(String description, String linkPost);

    /**
     * Metodo que realiza una peticion rest a postiz para mandar mensaje de la entrada de blog
     * en el canal de Discord
     * @param description Descripcion que se pondra en el mensaje de Discord para que alente a la
     * gente a ir al post
     * @param linkPost Link que se adjuntara al mensaje de Discord
     */
    public void toPostOnDiscord(String description, String linkPost);
}
