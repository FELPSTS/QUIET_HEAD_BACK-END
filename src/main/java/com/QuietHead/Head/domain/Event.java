package com.QuietHead.Head.domain;

import javax.xml.crypto.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Event {

    @Id
    @GeneratedValue
    
    private Long id;

    public String local;
    public Data data;
    private String participante;
    public Number carid;

}