package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Auto;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface AutoRepository extends Neo4jRepository<Auto, Long> {
    Optional<Auto> findById(String id);
}

