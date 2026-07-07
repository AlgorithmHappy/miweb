package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.InformationPostResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.PageResponse;
import dev.gerardomarquez.mail_whatsapp_send.services.ServicePostsCrud;

import org.springframework.data.domain.Sort;

@CrossOrigin(origins = "https://www.gerardomarquez.dev")
@RestController
@RequestMapping("/v1/blog")
public class BlogPosts {

    @Autowired
    private ServicePostsCrud servicePostsCrud;

    /**
     * Metodo que se ejecuta al enviar una peticion de tipo post en la url: /v1/messages/send para devolver
     * todos los posts (entradas del blog) para que los pueda compilar a html astro
     * @param contactMessage Request que mando el cliente con los atributos del formulario
     * de contacto
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PageResponse<InformationPostResponse> > > send(
        @PageableDefault(size = 6, sort = "date", direction = Sort.Direction.DESC)
        Pageable pageable,
        @RequestParam(required = false) String tag,
        @RequestParam(required = false) String title
    ) {
        ApiResponse<PageResponse<InformationPostResponse> > response = servicePostsCrud.findAllByPageAndTitleAndTag(pageable, tag, title);
        return ResponseEntity.ok(response);
    }
}