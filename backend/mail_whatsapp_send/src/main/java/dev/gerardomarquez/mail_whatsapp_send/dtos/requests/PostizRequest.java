package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import java.util.List;

public record PostizRequest(
    String type,
    String date,
    boolean shortLink,
    List<String> tags,
    List<Post> posts
) {
}
