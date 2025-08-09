package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface PostRepository extends Neo4jRepository<Post, Long> {
    @Query("MATCH (p:Post)-[:POSTED_BY]->(c:Client) WHERE id(c) = $clientId RETURN p")
    List<Post> findByAuthorId(Long clientId);
}