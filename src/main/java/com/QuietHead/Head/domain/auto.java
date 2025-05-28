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
@AllArgsConstructor
@NoArgsConstructor
@Node
public class auto {

    @id
    @GeneratedValue
        private Long id
        private long nameowner;
        public long id_owner;
        public String category;
        public String color;
        public Text Model;
        public YearDate year;

    @Relationship(type = "OWNER", direction = Relationship.Direction.INCOMING)
    private List<Cliente> owner;
}
