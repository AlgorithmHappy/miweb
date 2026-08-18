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

    public static final String SUCCESS_OPERATION = "Operación Exitosa";
    public static final Integer GENERIC_AVERAGE_DURATION = 45;
    public static final String URL_RAW_MARK_DOWN = "https://raw.githubusercontent.com/%s/%s/%s/%s";
    public static final String GENERIC_POST_DESCRIPTION = "Descripcion generica del post XD";

    public static final String GITHUB = "GITHUB";
    public static final String VERCEL = "VERCEL";

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String LINE_BREAK = "\n";

    public static final String JSON_NODE_CONTENT = "content";
    public static final String JSON_NODE_SHA = "sha";
    public static final String JSON_NODE_MESSAGE = "message";
    public static final String JSON_NODE_BRANCH = "branch";

    public static final String HEADER_LOCATION = "Location";

    public static final String SLASH = "/";

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    
    public static final Map<String, String> SORT_MAPPING = Map.of(
        "date", "createdAt",
        "title", "title"
    );

    /**
     * Mensajes de error
     */
    public static final String ERR_MSG_SERVICE_GITHUB_GETFILECONTENT = "service.github.getfilecontent";
    public static final String ERR_MSG_SERVICE_GITHUB_GETFILESHA = "service.github.getfilesha";
    public static final String ERR_MSG_SERVICE_GITHUB_UPDATEFILECONTENT = "service.github.updatefilecontent";
    public static final String ERR_MSG_SERVICE_HEDGEDOC_GETNOTECONTENT = "service.hedgedoc.getnotecontent";
    public static final String ERR_MSG_SERVICE_HEDGEDOC_CREATENOTE = "service.hedgedoc.createnote";
    public static final String ERR_MSG_SERVICE_SYNC_PAIR_NOT_FOUND = "service.sync.pair.not.found";
    public static final String ERR_MSG_SERVICE_SYNC_SHA_MISSING = "service.sync.sha.missing";
    public static final String ERR_MSG_SERVICE_SYNC_REPOSITORY_NOT_FOUND = "service.sync.repository.not.found";
    //public static final String ERR_MSG_SERVICE_SYNC_REPO_NOT_FOUND = "service.sync.repo.not.found";
    public static final String ERR_MSG_CONTROLLER_SYNC_PULL_SUCCESS = "controller.sync.pull.success";
    public static final String ERR_MSG_CONTROLLER_SYNC_PULL_GITHUB = "controller.sync.pull.error.github";
    public static final String ERR_MSG_CONTROLLER_SYNC_PULL_HEDGEDOC = "controller.sync.pull.error.hedgedoc";
    public static final String ERR_MSG_CONTROLLER_SYNC_PUSH_SUCCESS = "controller.sync.push.success";
    public static final String ERR_MSG_CONTROLLER_SYNC_PUSH_GITHUB = "controller.sync.push.error.github";
    public static final String ERR_MSG_CONTROLLER_SYNC_PUSH_HEDGEDOC = "controller.sync.push.error.hedgedoc";
    public static final String ERR_MSG_CONTROLLER_SYNC_LOGIN_ERROR = "controller.sync.login.error";
    public static final String ERR_MSG_CONTROLLER_SYNC_LOGOUT_ERROR = "controller.sync.login.logout";

    public static String calculateSignature(
        String payload,
        String secret,
        String kind
    ) throws Exception {

        Mac mac = null;
        SecretKeySpec keySpec = null;
        StringBuilder sb = null;
        if(kind.equalsIgnoreCase(GITHUB) ){
            mac = Mac.getInstance("HmacSHA256");
            keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sb = new StringBuilder("sha256=");
        } else if(kind.equalsIgnoreCase(VERCEL) ){
            mac = Mac.getInstance("HmacSHA1");
            keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            sb = new StringBuilder();
        } else {
            mac = Mac.getInstance("HmacSHA256");
            keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sb = new StringBuilder("sha256=");
        }

        mac.init(keySpec);

        byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8) );

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
