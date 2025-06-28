package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;

/*
 * Interface que define los metodos para el envio de emails
 */
public interface ServiceSendEmail {
    public void sendEmail(ContactMessage contactMessage);
}
