package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Message;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends Neo4jRepository<Message, Long> {

    @Query("MATCH (m:Message)-[:ENVIADA_POR]->(c:Client) WHERE ID(c) = $senderId RETURN m")
    List<Message> findBySenderId(Long senderId);

    @Query("MATCH (m:Message)-[:RECEBIDA_POR]->(c:Client) WHERE ID(c) = $receiverId RETURN m")
    List<Message> findByReceiverId(Long receiverId);
}
