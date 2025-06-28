package dev.gerardomarquez.mail_whatsapp_send.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Clase que devolvera el response con el error que pudiera ocurrir
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    /*
     * Codigo http de error devuelto 
     */
    private Integer httpStatusCode;
    /*
     * Mensaje concreto del error
     */
    private String errorMessage;
    /*
     * Stack del error
     */
    private String errorStack;
    /*
     * Mensaje de error que puede mostrar el front end
     */
    private String errorFriendlyMessage;
}
