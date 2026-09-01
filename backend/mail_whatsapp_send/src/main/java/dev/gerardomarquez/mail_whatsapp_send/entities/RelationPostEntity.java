package dev.gerardomarquez.mail_whatsapp_send.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "relations_posts")
public class RelationPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_origin_post", nullable = false)
    private PostEntity originPost;

    @ManyToOne
    @JoinColumn(name = "id_previous_post")
    private PostEntity previousPost;

    @ManyToOne
    @JoinColumn(name = "id_next_post")
    private PostEntity nextPost;

    @Column(name = "is_in_post_list", nullable = false)
    private Boolean isInPostList = false;
}
