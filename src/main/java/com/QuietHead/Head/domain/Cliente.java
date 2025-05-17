<<<<<<< HEAD
package com.QuietHead.Head.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Node

public class Cliente {

@Id
    private Long id;

    private String nome;
    private String email;
}
=======
package com.QuietHead.Head.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Node

public class Cliente {

@Id
    private Long id;

    private String nome;
    private String email;
}
>>>>>>> 21bb8000780e385f4329322ec3a46b35e2631191
