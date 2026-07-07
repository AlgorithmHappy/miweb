package dev.gerardomarquez.mail_whatsapp_send.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "repositories")
public class RepositoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name; // Nombre del repo en GitHub (ej. "curso-java")

    @Column(name = "owner", nullable = false, length = 100)
    private String owner; // Usuario u organización dueña del repo

    @Column(name = "github_token", nullable = false, length = 255)
    private String githubToken; // Personal Access Token con scope "repo"

    @Column(name = "default_branch", nullable = false, length = 100)
    private String defaultBranch = "main"; // Rama por defecto

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
