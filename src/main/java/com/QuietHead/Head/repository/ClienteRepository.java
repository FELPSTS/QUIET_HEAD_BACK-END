package com.QuietHead.Head.repository;
import com.QuietHead.Head.domain.Cliente;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface ClienteRepository extends Neo4jRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}