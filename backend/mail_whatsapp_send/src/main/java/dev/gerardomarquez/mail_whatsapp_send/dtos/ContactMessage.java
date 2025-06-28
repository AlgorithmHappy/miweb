package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ContactMessage implements Serializable {
    /*
     * Nombre completo del usuario que lleno el formulario
     */
    @NotBlank
    @Size(max = 100)
    private String fullName;
    /*
     * Correo electronico del usuario
     */
    @Email
    private String email;
    /*
     * Mensaje del usuario
     */
    @NotBlank
    private String message;
    /*
     * Fecha en la que se envio en mensaje en el formulario de contactos
     * este es ignorado al realizar el parseo a json
     */
    @JsonIgnore
    private LocalDateTime createdAt;

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
    public static ContactMessage entityToContactMessage(MessagesFromContactForm messagesFromContactForm) {
        ContactMessage entityConverted = new ContactMessage(
            messagesFromContactForm.getName(),
            messagesFromContactForm.getEmail(),
            messagesFromContactForm.getMessage(),
            messagesFromContactForm.getCreatedAt()
        );
        return entityConverted;
    }
}
