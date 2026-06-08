package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/webhooks/vercel")
public class VercelWebhookController {
    
    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody String payload) {

        System.out.println("Webhook recibido:");
        System.out.println(payload);

        return ResponseEntity.ok().build();
    }
}
