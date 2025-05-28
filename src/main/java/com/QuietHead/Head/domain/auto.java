package com.QuietHead.Head.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.w3c.dom.Text;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class auto {
    id dono;
    long id_dono;
    String categoria;
    String color;
    Text Model;
    YearDate year;
}
