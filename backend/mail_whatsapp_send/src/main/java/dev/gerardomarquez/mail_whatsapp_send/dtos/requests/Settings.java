package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Settings(
    @JsonProperty("__type")
    String type,
    String who_can_reply_post,
    String channel
) {
}
