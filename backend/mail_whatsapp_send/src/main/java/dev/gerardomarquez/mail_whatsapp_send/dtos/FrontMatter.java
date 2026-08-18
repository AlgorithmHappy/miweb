package dev.gerardomarquez.mail_whatsapp_send.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record FrontMatter(
        String title,
        LocalDateTime date,
        Integer readDuration,
        String description,
        List<String> tags,
        String image
) {}