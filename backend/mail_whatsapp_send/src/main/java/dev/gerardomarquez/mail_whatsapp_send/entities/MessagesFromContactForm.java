package dev.gerardomarquez.mail_whatsapp_send.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Clase de tipo entidad que creara la tabla en la base de datos en caso de que no exista y si
 * existe se mapeara con la existente. Con lombok se crean en automatico constructor vacio y
 * constructor con todos los parametros
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessagesFromContactForm {
    
    /*
     * Primera columna de la tabla, este sera un id autogenerado con un UUID
     */
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    /*
     * Nombre de la persona que lleno el formulario de contactos de la pagina gerardomarquez.dev
     */
    @Column
    private String name;

    /*
     * Correo electronico de contacto de la persona que lleno el formulario de la pagina
     * gerardomarquez.dev
     */
    @Column
    private String email;

    /*
     * Mensaje con el contenido del correo y de lo que mando la persona en el formulario de la
     * pagina gerardomarquez.dev
     */
    @Column
    private String message;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
