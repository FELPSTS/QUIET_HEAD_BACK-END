package com.QuietHead.Head.domain;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Node
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Comment {
 
    @Id
    @GeneratedValue
    
    private Long id;

    @Property
    @NotEmpty(message = "O conteúdo do comentário não pode estar vazio")
    private String content;

    @CreatedDate
    @Property("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Property("updated_at")
    private LocalDateTime updatedAt;

    @Relationship(type = "AUTHORED_BY", direction = Relationship.Direction.OUTGOING)
    private Client author;

    @Relationship(type = "COMMENTED_ON", direction = Relationship.Direction.OUTGOING)
    private Post post;
    
}