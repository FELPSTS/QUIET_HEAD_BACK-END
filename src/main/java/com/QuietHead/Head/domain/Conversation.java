package com.QuietHead.Head.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Conversation {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate createdAt = LocalDate.now(); // inicializa com a data atual

    @Relationship(type = "PARTICIPANT", direction = Relationship.Direction.OUTGOING)
    private List<Client> participants = new ArrayList<>();

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.Direction.OUTGOING)
    private List<Message> messages = new ArrayList<>();

    public Conversation() {}

    public Conversation(List<Client> participants) {
        this.participants = participants;
        this.createdAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Client> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Client> participants) {
        this.participants = participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        if (this.messages == null) this.messages = new ArrayList<>();
        this.messages.add(message);
    }
}
