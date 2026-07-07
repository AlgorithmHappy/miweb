package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import java.util.List;

public record Post(
    Integration integration,
    List<Value> value,
    Settings settings
) {
}
