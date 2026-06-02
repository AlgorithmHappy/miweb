package dev.gerardomarquez.mail_whatsapp_send.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.mail_whatsapp_send.services.ServiceGitHubWebHook;

@RestController
@RequestMapping("/v1/webhooks/github")
public class GitHubWebhookController {

    @Autowired
    ServiceGitHubWebHook serviceGitHubWebHook;

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
        @RequestHeader("X-Hub-Signature-256") String signature,
        @RequestHeader("X-GitHub-Event") String event,
        @RequestBody String payload
    ) {
        serviceGitHubWebHook.pushEventWebHook(payload, signature);        

        return ResponseEntity.ok().build();
    }
}
