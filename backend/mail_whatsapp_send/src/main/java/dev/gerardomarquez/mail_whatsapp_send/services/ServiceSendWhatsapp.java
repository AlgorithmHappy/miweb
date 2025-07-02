package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;

/*
 * Interface que define los metodos para el envio de whatsapp
 */
public interface ServiceSendWhatsapp {
    /*
     * Metodo que envia el mensaje de whatsapp
     * @param contactMessage Contenido del request body que manda el cliente a este servicio
     */
    public void sendWhatsappMessage(ContactMessage contactMessage);
}
