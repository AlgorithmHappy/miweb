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

    /*
     * Asunto a donde se va enviar
     */
    @Value("${send.mail.subject}")
    private String subject;

    /*
     * Correo electronico a donde se va enviar
     */
    @Value("${send.mail.mail}")
    private String mailTo;

    /*
     * Texto que tiene la plantilla de email que se enviara
     */
    @Value("${send.mail.template}")
    private String mailTemplate;

    /*
     * Objeto que envia los emails
     */
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
            String.format(
                mailTemplate,
                contactMessage.getFullName(),
                contactMessage.getEmail(),
                contactMessage.getMessage()
            )  
        );
        mailSender.send(message);
    }

}
