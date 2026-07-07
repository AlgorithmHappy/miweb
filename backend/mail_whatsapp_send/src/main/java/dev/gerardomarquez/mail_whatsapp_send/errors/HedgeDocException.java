package dev.gerardomarquez.mail_whatsapp_send.errors;

/**
 * Excepción lanzada cuando ocurre un error al comunicarse
 * con la API de HedgeDoc (obtener nota, crear nota).
 */
public class HedgeDocException extends RuntimeException {

    /**
     * @param message Mensaje descriptivo del error
     */
    public HedgeDocException(String message) {
        super(message);
    }

    /**
     * @param message Mensaje descriptivo del error
     * @param cause   Excepción original que causó el error
     */
    public HedgeDocException(String message, Throwable cause) {
        super(message, cause);
    }
}
