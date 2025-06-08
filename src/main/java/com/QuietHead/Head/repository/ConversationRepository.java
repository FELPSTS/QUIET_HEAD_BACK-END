package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Conversation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends Neo4jRepository<Conversation, Long> {
}
