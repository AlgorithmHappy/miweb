package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import java.util.List;

public record Value(
    String content,
    List<String> image
) {
}
