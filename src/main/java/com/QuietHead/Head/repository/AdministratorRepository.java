package com.QuietHead.Head.repository;
import com.QuietHead.Head.domain.Administrator;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AdministratorRepository extends Neo4jRepository<Administrator, Long> {
    Optional<Administrator> findByEmail(String email);
}