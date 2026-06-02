package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

@Service
public class ImplementationServiceGitHubWebHook implements ServiceGitHubWebHook {

    @Autowired
    private ServicePostsCrud postsCrud;

    @Value("${github.webhook.secret}")
    private String secret;

    /**
     * {@inheritDoc}
     */
    @Override
    public void pushEventWebHook(String json, String signature) {
        String expected = new String();
        try {
            expected = Constants.calculateSignature(json, signature);    
        } catch (Exception e) {
            System.out.println(e.getMessage() );
        }
        
        if(!expected.equals(secret) ){
            System.out.println("Secreto invalido, no hay coincidencia");
            return;
        }
            
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);

            JsonNode commits = jsonNode.get("commits");
            for (JsonNode commit : commits) {
                JsonNode added = commit.get("added");
                JsonNode removed = commit.get("removed");

                added.forEach(
                    (file) -> {
                        postsCrud.insertOne(
                            jsonNode.path("repository").path("owner").path("name").asText(),
                            jsonNode.path("repository").path("owner").path("name").asText(),
                            jsonNode.get("ref").asText(),
                            file.asText()
                        );
                        System.out.println("AGREGADO: " + file.asText() );
                    }
                );

                removed.forEach(
                    (file) -> {
                        postsCrud.deleteOne(file.asText() );
                        System.out.println("ELIMINADO: " + file.asText() );
                    }
                );

            }
        } catch (Exception e) {
            System.out.println("Hubo un error en el parseo del json");
        }
    }

}
