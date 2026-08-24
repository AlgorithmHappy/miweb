package dev.gerardomarquez.mail_whatsapp_send.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, length = 70, unique = true)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", updatable = true)
    private LocalDateTime createdAt;

    @Column(name = "average_read_duration", nullable = false)
    private Integer averageReadDuration;

    @Column(name = "description", nullable = false, length = 110)
    private String description;

    @Column(name = "link_raw_markdown", nullable = false, length = 250)
    private String linkRawMarkdown;

    @Column(name = "link_image", length = 250)
    private String linkImage;

    @Column(name = "alt_image", length = 70)
    private String altImage;

    @Column(nullable = false)
    private Boolean shared = false;

    @ManyToMany
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "id_post"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<TagEntity> tags = new HashSet<>();

    @OneToMany(mappedBy = "originPost")
    private Set<RelationPostEntity> relationPost;
}