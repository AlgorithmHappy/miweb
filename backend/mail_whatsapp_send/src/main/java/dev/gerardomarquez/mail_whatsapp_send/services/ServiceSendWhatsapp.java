package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.SendWhatsapp;

/*
 * Interface que define los metodos para el envio de whatsapp
 */
public interface ServiceSendWhatsapp {
    /*
     * Metodo que envia el mensaje de whatsapp
     * @param sendWhatsapp Contenido del request body
     */
    public void sendWhatsappMessage(SendWhatsapp sendWhatsapp);
}
