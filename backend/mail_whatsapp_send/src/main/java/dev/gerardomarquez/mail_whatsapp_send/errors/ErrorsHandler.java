package dev.gerardomarquez.mail_whatsapp_send.errors;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ErrorResponse;

/*
 * Clase que captura los errores que pudieran darse
 */
@RestControllerAdvice
public class ErrorsHandler {

    /*
     * Log que escribira los errores
     */
    private static final Logger logger = LoggerFactory.getLogger(ErrorsHandler.class);

    /*
     * Archivo properties que contiene los mensajes de error
     */
    @Autowired
    private MessageSource messageSource;

    /*
     * Metodo que se ejecuta al lanzarse un error inesperado
     * @param exception Error que fue lanzado
     */
    public ResponseEntity<ErrorResponse> serverError(Exception exception){
        Integer errorHttpStatusCode = Integer.parseInt(
            messageSource.getMessage(
                "http.status.code.error.server",
                null,
                Locale.getDefault()
            )
        );
        String stackTrace = getStackTraceAsString(exception);
        String friendlyMessage = messageSource.getMessage(
            "http.status.code.error.server.friendly.message",
            null,
            Locale.getDefault()
        );
        ErrorResponse response = new ErrorResponse(
            errorHttpStatusCode,
            exception.getMessage(),
            stackTrace,
            friendlyMessage
        );
        logger.error(response.toString() );
        return ResponseEntity.internalServerError().body(response);
    }

    /*
     * Metodo que se ejecuta al ocurrir un error en la validacion de los datos de entrada
     * @param exception Error que fue lanzado
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> serverError(MethodArgumentNotValidException exception){
        Integer errorHttpStatusCode = Integer.parseInt(
            messageSource.getMessage(
                "http.status.code.error.validated",
                null,
                Locale.getDefault()
            )
        );
        String stackTrace = getStackTraceAsString(exception);
        String friendlyMessage = messageSource.getMessage(
            "http.status.code.error.validated.friendly.message",
            null,
            Locale.getDefault()
        );
        ErrorResponse response = new ErrorResponse(
            errorHttpStatusCode,
            exception.getMessage(),
            stackTrace,
            friendlyMessage
        );
        logger.error(response.toString() );
        return ResponseEntity.badRequest().body(response);
    }

    /*
     * Metodo para convertir el stacktrace en un string
     * @param exception Excepcion que se lanzo para que ocurriera el error
     */
    private String getStackTraceAsString(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }
}
