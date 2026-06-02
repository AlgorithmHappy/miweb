package dev.gerardomarquez.mail_whatsapp_send.errors;

/**
 * Excepcion general que se lanzara al haber algun error en el consumo de la api
 * de telegram
 */
public class TelegramApiException extends RuntimeException{
    
    public TelegramApiException(String message) {
        super(message);
    }

    public TelegramApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
