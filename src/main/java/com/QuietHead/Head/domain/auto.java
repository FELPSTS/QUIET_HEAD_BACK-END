package com.QuietHead.Head.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Auto {

    @Id
    @GeneratedValue
    private Long id;

    private String modelo;
    private String categoria;
    private String cor;
    private int ano;

    @Relationship(type = "OWNER", direction = Relationship.Direction.INCOMING)
    private List<Client> owner;
}
