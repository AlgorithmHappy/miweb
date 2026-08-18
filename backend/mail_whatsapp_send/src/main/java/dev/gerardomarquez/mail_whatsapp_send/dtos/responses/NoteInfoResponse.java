package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

public record NoteInfoResponse(
        String title,
        String description,
        Integer viewcount,
        String createtime,
        String updatetime
) {
}