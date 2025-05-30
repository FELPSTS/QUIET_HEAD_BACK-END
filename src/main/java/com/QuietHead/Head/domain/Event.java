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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    private String local;
    private LocalDate data;

    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private List<Administrator> administrador;

    @Relationship(type = "PARTICIPANT", direction = Relationship.Direction.INCOMING)
    private List<Cliente> participantes;

}
