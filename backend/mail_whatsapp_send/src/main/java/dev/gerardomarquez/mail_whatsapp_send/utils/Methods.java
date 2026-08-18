package dev.gerardomarquez.mail_whatsapp_send.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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
                null,
                null
            );
        }
        
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory() ).findAndRegisterModules();
        FrontMatter metadata = new FrontMatter(
            null,
            null,
            null,
            null,
            null,
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
            return null;
        }

        return text.length() > maxLength
                ? text.substring(0, maxLength) + "..."
                : text;
    }
}