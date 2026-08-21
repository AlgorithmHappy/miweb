package dev.gerardomarquez.mail_whatsapp_send.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.Normalizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.gerardomarquez.mail_whatsapp_send.dtos.FrontMatter;
import dev.gerardomarquez.mail_whatsapp_send.services.ImplementationServiceSync;

public final class Methods {

    private static final Logger log = LoggerFactory.getLogger(ImplementationServiceSync.class);

    public static FrontMatter getFrontMetterToMap(String markdown){
        Pattern pattern = Pattern.compile(
                "\\A---\\R(.*?)\\R---(?:\\R|$)",
                Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(markdown);

        String frontMatter = matcher.find()
            ? matcher.group(1)
            : "";

        if(frontMatter.isEmpty() ){
            return new FrontMatter(
                null,
                null,
                null,
                null,
                List.of(),
                null
            );
        }
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory() ).findAndRegisterModules();
        FrontMatter metadata = new FrontMatter(
            null,
            null,
            null,
            null,
            List.of(),
            null
        );

        try {
            metadata = mapper.readValue(frontMatter, FrontMatter.class);
        } catch (Exception e) {
            log.error("Error parsing front matter: " + e.getMessage(), e);
        }
        
        return metadata;
    }

    public static String truncate(String text, int maxLength) {
        if (text == null) {
            return new String();
        }

        return text.length() > maxLength
                ? text.substring(0, maxLength - 3) + "..."
                : text;
    }

    public static String toSlug(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")          // quita acentos
            .replaceAll("[^a-zA-Z0-9\\s-]", "") // quita símbolos
            .trim()
            .replaceAll("\\s+", "-")           // espacios -> -
            .replaceAll("-+", "-")             // evita -- --
            .toLowerCase();
    }

}