package dev.gerardomarquez.mail_whatsapp_send.dtos.responses;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum que representa los tipos de objetos del response de GitHub.
 */
public enum GitObjectType {
    @JsonProperty("blob")
    BLOB,

    @JsonProperty("tree")
    TREE,

    @JsonProperty("commit")
    COMMIT,

    @JsonEnumDefaultValue
    UNKNOWN
}