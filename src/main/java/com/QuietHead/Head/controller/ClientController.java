package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.dto.LoginResponse;
import com.QuietHead.Head.security.JwtUtil;
import com.QuietHead.Head.service.ClientService;

import jakarta.validation.Valid;

import com.QuietHead.Head.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final JwtUtil jwtuntil;

    @Autowired
    public ClientController(ClientService clientService, JwtUtil jwtuntil) {
        this.clientService = clientService;
        this.jwtuntil = jwtuntil;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        log.info("Requisição recebida - Email: {}", client.getEmail());

        Client createdClient = clientService.createClient(client);

        log.info("Resposta enviada (HTTP 201) - Cliente criado: {}", createdClient);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Client> updateClient(
            @PathVariable String email,
            @RequestBody Client clientUpdate) {
        return clientService.updateClient(email, clientUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteClient(@PathVariable String email) {
        return clientService.deleteClientByEmail(email)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    try {
        Client client = clientService.authenticate(
            loginRequest.email(),
            loginRequest.password()
        );
        
        String token = jwtuntil.generateToken(client.getEmail());
        
        return ResponseEntity.ok(new LoginResponse(
            token,
            client.getId(),
            client.getEmail(),
            client.getName()
        ));
        }
    catch (IllegalArgumentException e) {
        log.error("Erro de autenticação: {}", e.getMessage());   
        }
    return null;
    }
}