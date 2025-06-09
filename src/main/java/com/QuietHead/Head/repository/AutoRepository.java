package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Auto;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AutoRepository extends Neo4jRepository<Auto, Long> {
    Optional<Auto> findById(Long id);
}

