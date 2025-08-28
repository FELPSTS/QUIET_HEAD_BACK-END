package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Client;
import org.springframework.data.neo4j.repository.Neo4jRepository;


import java.util.Optional;

public interface ClientRepository extends Neo4jRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Boolean existsByEmail(String email);

}

