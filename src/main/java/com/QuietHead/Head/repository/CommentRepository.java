package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Comment;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface CommentRepository extends Neo4jRepository<Comment, Long> {
    
    @Query("MATCH (c:Comment)-[:COMMENTED_ON]->(p:Post) WHERE id(p) = $postId RETURN c")
    List<Comment> findByPostId(Long postId);
    
    @Query("MATCH (c:Comment)-[:AUTHORED_BY]->(u:Client) WHERE id(u) = $clientId RETURN c")
    List<Comment> findByAuthorId(Long clientId);
    
    @Query("MATCH (c:Comment)-[:COMMENTED_ON]->(p:Post) WHERE id(p) = $postId DELETE c")
    void deleteByPostId(Long postId);
}