package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;
import dev.gerardomarquez.mail_whatsapp_send.services.ImplementationServiceSendEmail;
import dev.gerardomarquez.mail_whatsapp_send.services.ImplementationServiceSendWhatsapp;
import dev.gerardomarquez.mail_whatsapp_send.services.ServiceMessagesCrud;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * Controlador que expone los endpoinst de los mensajes que enviad del formulario de 
 * contacto de la pagina gerardomarquez.dev
 */
@CrossOrigin(origins = "http://localhost:4321")
@Controller
@RequestMapping("/v1/contact/message")
public class MessagesEndPoint {

    /*
     * Servicio CRUD que inserta los mensajes en una base de datos
     */
    @Autowired
    private ServiceMessagesCrud serviceMessagesCrud;

    @Autowired
    private ImplementationServiceSendEmail serviceSendEmail;

    @Autowired
    private ImplementationServiceSendWhatsapp serviceSendWhatsapp;

    /*
     * Metodo que se ejecuta al enviar una peticion de tipo post en la url: /v1/messages/send
     * @param contactMessage Request que mando el cliente con los atributos del formulario
     * de contacto
     */
    @PostMapping("/send")
    public ResponseEntity<Void> send(@Valid @RequestBody ContactMessage contactMessage) {
        serviceMessagesCrud.insertOne(contactMessage);
        serviceSendEmail.sendEmail(contactMessage);
        serviceSendWhatsapp.sendWhatsappMessage(contactMessage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
