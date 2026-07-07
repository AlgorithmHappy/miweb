package dev.gerardomarquez.mail_whatsapp_send.errors;

/**
 * Excepción lanzada cuando ocurre un error al comunicarse
 * con la API de GitHub (obtener archivo, actualizar archivo, obtener SHA).
 */
public class GitHubException extends RuntimeException {

    /**
     * @param message Mensaje descriptivo del error
     */
    public GitHubException(String message) {
        super(message);
    }

    /**
     * @param message Mensaje descriptivo del error
     * @param cause   Excepción original que causó el error
     */
    public GitHubException(String message, Throwable cause) {
        super(message, cause);
    }
}
