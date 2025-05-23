package com.QuietHead.Head.repository;
import com.QuietHead.Head.domain.Event;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface EventRepository extends Neo4jRepository<Event, Long> {
    Optional<Event> findById(Long id);
}