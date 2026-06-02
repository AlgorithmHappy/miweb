package dev.gerardomarquez.mail_whatsapp_send.services;

/**
 * Interfaz qeu define lo que se va hacer cuando alguien haga push al repositorio
 */
public interface ServiceGitHubWebHook {

    /**
     * Metodo que guardara en base de datos en el caso de que se agregue un nuevo documento
     * markdown, que reconstruira el proyecto astro para agregar las nuevas entradas o modificaciones
     * que lanzara una publicacion en facebook, x, linkedin etc.
     * @param json Request en formato json que realiza github para el evento del push
     * @param signature Firma para saber que la peticion fue realizada desde github y no por otra parte
     */
    public void pushEventWebHook(String json, String signature);
}
