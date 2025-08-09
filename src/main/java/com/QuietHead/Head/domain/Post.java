package com.QuietHead.Head.domain;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    
    @GeneratedValue
    @Id
    private Long id;

    @Property("title")
    @NotEmpty(message = "O título não pode estar vazio")
    private String title;

    @Property("content")
    @NotEmpty(message = "O conteúdo não pode estar vazio")
    private String content;

    @Property("image_url")
    private String imageUrl;

    @Property("like_count")
    @Builder.Default
    private Integer likeCount = 0;

    @CreatedDate
    @Property("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updated_at")
    private LocalDateTime updatedAt;

    @Relationship(type = "POSTED_BY", direction = Relationship.Direction.OUTGOING)
    private Client author;
    
    @Relationship(type = "HAS_COMMENT", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        this.comments.add(comment);
    }

    public void incrementLikes() {
        if (likeCount == null) {
            likeCount = 0;
        }
        this.likeCount++;
    }

    public void decrementLikes() {
        if (likeCount == null) {
            likeCount = 0;
        }
        this.likeCount = Math.max(0, this.likeCount - 1);
    }
}