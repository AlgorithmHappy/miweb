package dev.gerardomarquez.mail_whatsapp_send.configurations;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import dev.gerardomarquez.mail_whatsapp_send.dtos.requests.Post;

@Component
@ConfigurationProperties(prefix = "postiz.posts")
public class PostizProperties {
    
    private List<Post> posts;

    // getters y setters
    public List<Post> getPosts() { return posts; }
    public void setPosts(List<Post> posts) { this.posts = posts; }
}
