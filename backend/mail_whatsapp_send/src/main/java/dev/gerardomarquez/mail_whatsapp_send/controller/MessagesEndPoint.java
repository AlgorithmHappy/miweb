package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ContactMessage;
import dev.gerardomarquez.mail_whatsapp_send.services.ServiceMessagesCrud;

import org.springframework.web.bind.annotation.PostMapping;

/*
 * Controlador que expone los endpoinst de los mensajes que enviad del formulario de 
 * contacto de la pagina gerardomarquez.dev
 */
@Controller
@RequestMapping("/v1/contact/message")
public class MessagesEndPoint {

    /*
     * Servicio CRUD que inserta los mensajes en una base de datos
     */
    @Autowired
    private ServiceMessagesCrud serviceMessagesCrud;

    /*
     * Metodo que se ejecuta al enviar una peticion de tipo pos en la url: /v1/messages/send
     * @param contactMessage Request que mando el cliente con los atributos del formulario
     * de contacto
     */
    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody ContactMessage contactMessage) {
        serviceMessagesCrud.insertOne(contactMessage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
