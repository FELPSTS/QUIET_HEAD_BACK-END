package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends Neo4jRepository<Post, Long> {
    
    List<Post> findByTitleContaining(String title);
    
    @Query("MATCH (p:Post) WHERE id(p) = $postId RETURN p")
    Optional<Post> findByIdWithComments(@Param("postId") Long postId);
    
    @Query("MATCH (p:Post) WHERE id(p) = $postId OPTIONAL MATCH (c:Comment)-[:COMMENTED_ON]->(p) RETURN p, collect(c)")
    Optional<Post> findPostWithComments(@Param("postId") Long postId);
    
    @Query("MATCH (p:Post) OPTIONAL MATCH (c:Comment)-[:COMMENTED_ON]->(p) RETURN p, collect(c)")
    List<Post> findAllWithComments();
}