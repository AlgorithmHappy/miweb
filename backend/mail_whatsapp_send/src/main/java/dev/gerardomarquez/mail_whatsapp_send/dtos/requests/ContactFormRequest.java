package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import java.io.Serializable;

import dev.gerardomarquez.mail_whatsapp_send.entities.MessagesFromContactForm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * Request que hara el usuario donde vendra el mensaje del usuario
 */
@Getter
@Setter
@AllArgsConstructor
public class ContactFormRequest implements Serializable {
    /*
     * Nombre completo del usuario que lleno el formulario
     */
    @NotBlank(message = "{not.blank.name}")
    @Size(max = 70)
    private String fullName;
    /*
     * Correo electronico del usuario
     */
    @Email(message = "{email.email}")
    @NotBlank(message = "{not.blank.email}")
    private String email;
    /*
     * Mensaje del usuario
     */
    @NotBlank(message = "{not.blank.message}")
    @Size(max = 500, message = "{validation.size}")
    private String message;

    /*
     * Metodo que convierte el objeto actual "this" a una entidad compatible con
     * la base de datos
     */
    public MessagesFromContactForm toEntity() {
        MessagesFromContactForm entity = new MessagesFromContactForm();
        entity.setName(this.fullName);
        entity.setEmail(this.email);
        entity.setMessage(this.message);
        return entity;
    }

    /*
     * Metodo que convierte un objeto de tipo MessagesFromContactForm a tipo ContactMessage
     * @param messagesFromContactForm Objeto que se va a converti a ContactMessage
     */
    public static ContactFormRequest entityToContactMessage(MessagesFromContactForm messagesFromContactForm) {
        ContactFormRequest entityConverted = new ContactFormRequest(
            messagesFromContactForm.getName(),
            messagesFromContactForm.getEmail(),
            messagesFromContactForm.getMessage()
        );
        return entityConverted;
    }
}
