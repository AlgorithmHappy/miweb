package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response de GitHub al hacer pull
 * @param name
 * @param path
 * @param sha
 * @param size
 * @param url
 * @param htmlUrl
 * @param gitUrl
 * @param downloadUrl
 * @param type
 * @param content
 * @param encoding
 * @param links
 */
public record GitHubFileResponse(
        String name,
        String path,
        String sha,
        Long size,
        String url,

        @JsonProperty("html_url")
        String htmlUrl,

        @JsonProperty("git_url")
        String gitUrl,

        @JsonProperty("download_url")
        String downloadUrl,

        String type,
        String content,
        String encoding,

        @JsonProperty("_links")
        GitHubLinks links
) {
}
