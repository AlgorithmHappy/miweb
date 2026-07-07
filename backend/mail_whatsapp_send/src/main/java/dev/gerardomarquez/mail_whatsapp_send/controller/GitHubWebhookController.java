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
@RequestMapping("/v1/webhooks")
public class GitHubWebhookController {

    @Autowired
    ServiceGitHubWebHook serviceGitHubWebHook;

    /**
     * Metodo webhook que se ejecuta cuando github detecta que hubo un cambio en los markdown de los
     * repositorios seleccionados, manda una peticion a este endpoint para avisar que hubo cambios
     * @param signature Secreto de github para saber que la peticion fue de github y no de otro lugar
     * @param event Evento que indica que fue lo que paso
     * @param payload Body del request que mando github
     * @return No devuelve nada
     */
    @PostMapping("/github")
    public ResponseEntity<Void> handleWebhook(
        @RequestHeader("X-Hub-Signature-256") String signature,
        @RequestHeader("X-GitHub-Event") String event,
        @RequestBody String payload
    ) {
        serviceGitHubWebHook.pushEventWebHook(payload, signature);        

        return ResponseEntity.ok().build();
    }
}
