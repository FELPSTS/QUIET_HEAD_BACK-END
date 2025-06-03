package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Conversation;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ConversationRepository extends Neo4jRepository<Conversation, long>{
    Optional<Conversation> findById(long id);
}
