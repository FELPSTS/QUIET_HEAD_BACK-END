package com.QuietHead.Head.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node
public class Mensagem {

    @Id
    @GeneratedValue
    private Long id;

    private String texto;
    private LocalDateTime dataHora;
    private boolean lida;

    @Relationship(type = "ENVIADA_POR", direction = Relationship.Direction.OUTGOING)
    private List<Client> remetente;

    @Relationship(type = "RECEBIDA_POR", direction = Relationship.Direction.OUTGOING)
    private List<Administrator> destinatario;
}
