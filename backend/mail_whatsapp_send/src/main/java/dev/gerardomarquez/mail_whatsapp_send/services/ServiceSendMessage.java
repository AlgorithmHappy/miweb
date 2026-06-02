package dev.gerardomarquez.mail_whatsapp_send.services;

/*
 * Interface que define los metodos para el envio de mensajes
 */
public interface ServiceSendMessage {
    /**
     * Metodo que envia el mensaje
     * @param request Contenido del request body que manda el cliente al servicio
     */
    public void sendMessage(Record request);
}
