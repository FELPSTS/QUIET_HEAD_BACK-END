package com.QuietHead.Head.domain;

import org.springframework.data.annotation.*;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Event;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import java.util.List;

@Node
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auto {

    @Id
    @GeneratedValue
    private Long id;

    private String modelo;
    private String category;
    private String cor;
    private Integer year;

    @Relationship(type = "OWNED_BY", direction = Relationship.Direction.OUTGOING)
    private Client owner;

    @Relationship(type = "PARTICIPATES_IN", direction = Relationship.Direction.OUTGOING)
    private List<Event> events;
}
