package com.QuietHead.Head.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    private LocalDateTime dataHora = LocalDateTime.now();
    private boolean lida;

     @Relationship(type = "SENT_BY", direction = Relationship.Direction.OUTGOING)
    private Client sender;

     @Relationship(type = "RECEIVED_BY", direction = Relationship.Direction.OUTGOING)
    private Client receiver;  

     @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private Conversation conversation;


}
