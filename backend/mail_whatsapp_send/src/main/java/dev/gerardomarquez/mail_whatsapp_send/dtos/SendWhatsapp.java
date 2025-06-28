package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * Clase que se convertira en json para el envio de whatsapp para evolution api
 */
@Setter
@Getter
@AllArgsConstructor
public class SendWhatsapp implements Serializable {

    /*
     * Numero de telefo que va a recivir la notificacion por whatsapp
     */
    private String number;
    /*
     * Mensaje de texto que se va enviar por whatsapp
     */
    private String text;
}
