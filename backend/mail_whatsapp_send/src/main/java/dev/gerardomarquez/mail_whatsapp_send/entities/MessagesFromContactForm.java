package dev.gerardomarquez.mail_whatsapp_send.entities;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Nombre de la persona que lleno el formulario de contactos de la pagina gerardomarquez.dev
     */
    @Column(length = 70)
    private String name;

    /*
     * Correo electronico de contacto de la persona que lleno el formulario de la pagina
     * gerardomarquez.dev
     */
    @Column(length = 70)
    private String email;

    /*
     * Mensaje con el contenido del correo y de lo que mando la persona en el formulario de la
     * pagina gerardomarquez.dev
     */
    @Column(length = 500)
    private String message;

    /*
     * Fecha en la que se envio el mensaje 
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
