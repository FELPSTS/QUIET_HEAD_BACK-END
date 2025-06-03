package com.QuietHead.Head.domain;

import java.lang.annotation.Repeatable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import jakarta.annotation.Generated;

@Node
public class Conversation {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate createdAt;

    @Relationship(type = "PARTICIPATES", direction = Relationship.Direction.INCOMING)
    private List<Client> participants;

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.Direction.OUTGOING)
    private List<Message> messages;
}
