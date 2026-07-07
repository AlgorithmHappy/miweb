package dev.gerardomarquez.mail_whatsapp_send.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.mail_whatsapp_send.entities.PostEntity;
import dev.gerardomarquez.mail_whatsapp_send.services.ServicePostsCrud;
import dev.gerardomarquez.mail_whatsapp_send.services.ServiceSocialNetwork;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

@RestController
@RequestMapping("/v1/webhooks")
public class VercelWebhookController {

    @Autowired
    private ServicePostsCrud servicePostCrud;
    
    @Value("${mypage.url.base}")
    private String myPageurlBase;

    @Value("${vercel.webhook.secret}")
    private String signingSecret;

    @Autowired
    private ServiceSocialNetwork serviceSocialNetwork;

    /**
     * Metodo WebHook que manda a llamar vercel cuando hay un nuevo rebuild o redespliegue en vercel
     * @param signature Secreto de vercel para saber que la peticion fue desde vercel y no de cualquier
     * otro lugar
     * @param payload Cuerpo de la peticion, el body json
     * @return Respuesta satisfactoria
     */
    @PostMapping("/vercel")
    public ResponseEntity<Void> receive(
        @RequestHeader("x-vercel-signature") String signature,
        @RequestBody String payload
    ) {
        System.out.println("Webhook recibido:");
        System.out.println(payload);

        String computed = new String();
        Boolean isValidSignature = false;
        try {
            computed = Constants.calculateSignature(payload, signingSecret, Constants.VERCEL);
            isValidSignature = MessageDigest.isEqual(
                computed.getBytes(StandardCharsets.UTF_8),
                signature.getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("isValidSignature: " + isValidSignature);

        if(!isValidSignature) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        PostEntity postEntity = servicePostCrud.findLastPost();
        String linkToPost = myPageurlBase + URLEncoder.encode(postEntity.getTitle(), StandardCharsets.UTF_8);

        String description = postEntity.getDescription();

        serviceSocialNetwork.toPostOnSocialNetworks(description, linkToPost);

        return ResponseEntity.ok().build();
    }
}
