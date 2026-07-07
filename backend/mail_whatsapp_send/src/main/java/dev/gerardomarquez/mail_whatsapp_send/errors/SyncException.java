package dev.gerardomarquez.mail_whatsapp_send.errors;

/**
 * Excepción lanzada cuando ocurre un error de lógica durante
 * el proceso de sincronización entre HedgeDoc y GitHub.
 * Por ejemplo: par nota-repo no encontrado, SHA desactualizado,
 * o datos incompletos en posts_sync.
 */
public class SyncException extends RuntimeException {

    /**
     * @param message Mensaje descriptivo del error
     */
    public SyncException(String message) {
        super(message);
    }

    /**
     * @param message Mensaje descriptivo del error
     * @param cause   Excepción original que causó el error
     */
    public SyncException(String message, Throwable cause) {
        super(message, cause);
    }
}
