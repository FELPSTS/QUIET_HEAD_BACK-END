package com.QuietHead.Head.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Year;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class auto {

    @Id
    @GeneratedValue
    private Long id;

    private String category;
    private String color;
    private String model;
    private Year year;

    @Relationship(type = "OWNER", direction = Relationship.Direction.INCOMING)
    private List<Cliente> owner;
}
