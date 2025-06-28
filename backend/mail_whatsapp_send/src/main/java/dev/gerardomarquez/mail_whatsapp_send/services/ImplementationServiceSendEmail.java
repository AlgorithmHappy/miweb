package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;

/*
 * Clase de implementacion
 * {@inheritDoc}
 */
@Service
public class ImplementationServiceSendEmail implements ServiceSendEmail{

    @Value("${send.mail.subject}")
    private String subject;

    @Value("${send.mail.mail}")
    private String mailTo;

    @Autowired
    private JavaMailSender mailSender;

    /*
    * {@inheritDoc}
    */
    @Override
    public void sendEmail(ContactMessage contactMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo); // Cambia por el destinatario real o usa contactMessage.getEmail()
        message.setSubject(subject);
        message.setText(
            "Nombre: " + contactMessage.getFullName() + "\n" +
            "Correo: " + contactMessage.getEmail() + "\n" +
            "Mensaje: " + contactMessage.getMessage()
        );
        mailSender.send(message);
    }

}
