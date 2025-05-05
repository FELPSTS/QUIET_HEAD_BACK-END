package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Cliente;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClienteRepository extends Neo4jRepository<Cliente, Long> {
}//precisamos configurar o banco