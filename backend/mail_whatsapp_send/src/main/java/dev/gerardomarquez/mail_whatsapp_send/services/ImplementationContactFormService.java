package dev.gerardomarquez.mail_whatsapp_send.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mail_whatsapp_send.dtos.ApiResponse;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.ContactFormRequest;
import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.MessageTelegramRequest;
import dev.gerardomarquez.mail_whatsapp_send.utils.Constants;

/**
 * Clase que implementa el metodo que se ejecuta cuando llenan el formulario de contacto y que
 * a su vez realiza el envio del mensaje y la inserccion de datos en la base de datos
 */
@Service
public class ImplementationContactFormService implements ContactFormService {

    /**
     * Servicio que envia el mensaje de telegram
     */
    @Autowired
    private ServiceSendMessage serviceSendMessage;

    /**
     * Servicio que inserta el mensaje en la base de datos
     */
    @Autowired
    private ServiceMessagesCrud serviceMessagesCrud;

    /**
     * Id del chat donde se va a enviar el mensaje
     */
    @Value("${telegram.api.chat.id}")
    private Long telegramApiChatId;

    @Value("${contact.from.message}")
    private String fullTextMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public ApiResponse<?> sendAction(ContactFormRequest request) {
        serviceMessagesCrud.insertOne(request);
        
        MessageTelegramRequest requestTelegram = new MessageTelegramRequest(
            telegramApiChatId,
            String.format(fullTextMessage, request.getFullName(), request.getEmail(), request.getMessage() )
        );
        serviceSendMessage.sendMessage(requestTelegram);

        return new ApiResponse<>(true, Constants.SUCCESS_OPERATION, null);
    }

}
