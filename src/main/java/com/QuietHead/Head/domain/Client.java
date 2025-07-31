package com.QuietHead.Head.domain;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Node
public class Client {

    @Id
    @GeneratedValue(GeneratedValue.UUIDGenerator.class)
    private Long id;

    private String name;
    private String email;
    private String password;

}