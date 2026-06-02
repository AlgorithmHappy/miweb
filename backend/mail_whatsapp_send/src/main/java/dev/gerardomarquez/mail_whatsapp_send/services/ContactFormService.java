package dev.gerardomarquez.mail_whatsapp_send.services;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.ContactFormRequest;

/**
 * Interfaz que ejecuta el servicio de el formulatrio de contacto
 */
public interface ContactFormService {

    /**
     * Metodo que se ejecuta al presionar el boton del formulario de contacto
     * @param request
     */
    public ApiResponse<?> sendAction(ContactFormRequest request);
}
