package com.QuietHead.Head.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Conversation {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate createdAt = LocalDate.now();

    @Relationship(type = "PARTICIPANT", direction = Relationship.Direction.OUTGOING)
    private List<Client> participants = new ArrayList<>();

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.Direction.OUTGOING)
    private List<Message> messages = new ArrayList<>();

    public Conversation(List<Client> participants) {
        this.participants = participants;
        this.createdAt = LocalDate.now();
    }

    public void addMessage(Message message) {
        if (messages == null) messages = new ArrayList<>();
        messages.add(message);
    }
}
