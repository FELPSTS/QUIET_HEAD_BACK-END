package com.QuietHead.Head.domain;
 
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import java.time.LocalDate;
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
    public LocalDate  data;
    public String participant;
    public Long carid;

    @Relationship(type = "CREATE BY", direction = Relationship.Direction.OUTGOING)
    private Administrador administrador;

    @Relationship(type = "PARTICIPANT", direction = Relationship.Direction.INCOMING)
    private List<Cliente> participantes;
}
