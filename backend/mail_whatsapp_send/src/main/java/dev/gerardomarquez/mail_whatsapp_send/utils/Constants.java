package dev.gerardomarquez.mail_whatsapp_send.utils;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

public final class Constants {
    private Constants() {

    }

    public static final String SUCCESS = "Operación Exitosa";
    public static final Integer GENERIC_AVERAGE_DURATION = 45;
    public static final String URL_RAW_MARK_DOWN = "https://raw.githubusercontent.com/%s/%s/%s/%s";
    public static final String GENERIC_POST_DESCRIPTION = "Descripcion generica del post XD";
    
    public static final Map<String, String> SORT_MAPPING = Map.of(
        "date", "createdAt",
        "title", "title"
    );

    public static String calculateSignature(
        String payload,
        String secret
    ) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");

        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        mac.init(keySpec);

        byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8) );

        StringBuilder sb = new StringBuilder("sha256=");

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String encodePath(String path) {
        return Arrays
            .stream(path.split("/") )
            .map(segment -> URLEncoder.encode(segment, StandardCharsets.UTF_8).replace("+", "%20") )
            .collect(Collectors.joining("/") ); 
    }
}
