package dev.gerardomarquez.mail_whatsapp_send.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_sync")
public class PostSyncEntity {
    /**
     * id_post es PK y FK al mismo tiempo (relación 1 a 1 con Post).
     * @MapsId indica que esta entidad compartirá la PK de la relación "post".
     */
    @Id
    @Column(name = "id_post")
    private Integer idPost;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
        name = "id_post",
        nullable = false,
        unique = true
    )
    private PostEntity post; // Relación hacia la entidad Post existente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_repository", nullable = false)
    private RepositoryEntity repository; // Repositorio de GitHub asociado

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath; // Ruta del archivo .md dentro del repo (ej. "docs/intro.md")

    @Column(name = "hedgedoc_note_id", nullable = false, length = 255)
    private String hedgedocNoteId; // ID de la nota en HedgeDoc

    @Column(name = "github_sha", length = 100)
    private String githubSha; // SHA actual del archivo en GitHub (necesario para push sin conflicto)

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt; // Última sincronización exitosa (pull o push)

}
