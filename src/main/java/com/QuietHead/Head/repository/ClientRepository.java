package com.QuietHead.Head.repository;

import com.QuietHead.Head.domain.Client;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends Neo4jRepository<Client, UUID> {  // Usando UUID em vez de Long
    
    // Busca por email (case-insensitive)
    Optional<Client> findByEmailIgnoreCase(String email);

    // Verifica se email existe
    boolean existsByEmailIgnoreCase(String email);

    // Busca por nome (usando LIKE)
    @Query("MATCH (c:Client) WHERE c.name CONTAINS $name RETURN c")
    List<Client> findByNameContaining(@Param("name") String name);

    // Atualização customizada
    @Query("MATCH (c:Client {email: $email}) SET c.name = $name, c.password = $password RETURN c")
    Optional<Client> updateClientByEmail(
        @Param("email") String email,
        @Param("name") String name,
        @Param("password") String password
    );

    // Delete por email
    @Query("MATCH (c:Client {email: $email}) DETACH DELETE c")
    void deleteByEmail(@Param("email") String email);
}