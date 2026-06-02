package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.ContactFormRequest;
import dev.gerardomarquez.mail_whatsapp_send.services.ContactFormService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * Controlador que expone los endpoinst de los mensajes que enviad del formulario de 
 * contacto de la pagina gerardomarquez.dev
 */
@CrossOrigin(origins = "https://www.gerardomarquez.dev")
@Controller
@RequestMapping("/v1/contact")
public class MessagesContactForm {

    /*
     * Servicio que envia el mensaje y lo guarda en la base de datos
     */
    @Autowired
    private ContactFormService contactFormService;

    /*
     * Metodo que se ejecuta al enviar una peticion de tipo post en la url: /v1/messages/send
     * @param contactMessage Request que mando el cliente con los atributos del formulario
     * de contacto
     */
    @PostMapping("/messages")
    public ResponseEntity<ApiResponse<?> > send(@Valid @RequestBody ContactFormRequest contactMessage) {
        ApiResponse<?> response = contactFormService.sendAction(contactMessage);
        return ResponseEntity.ok(response);
    }
}
