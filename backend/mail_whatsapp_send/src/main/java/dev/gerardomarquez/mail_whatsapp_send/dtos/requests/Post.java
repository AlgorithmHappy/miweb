package dev.gerardomarquez.mail_whatsapp_send.dtos.requests;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integration integration;
    private List<Value> value = new ArrayList<>();
    private Settings settings;
}
