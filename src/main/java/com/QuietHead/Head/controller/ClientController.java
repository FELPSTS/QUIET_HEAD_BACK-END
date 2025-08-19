package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.dto.LoginResponse;
import com.QuietHead.Head.security.JwtUtil;
import com.QuietHead.Head.service.ClientService;
import com.QuietHead.Head.exception.AuthenticationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        log.info("Creating client with email: {}", client.getEmail());
        
        Client createdClient = clientService.createClient(client);
        
        log.info("Client created successfully: {}", createdClient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        log.debug("Fetching all clients");
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        log.debug("Fetching client by ID: {}", id);
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        log.debug("Fetching client by email: {}", email);
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Client> updateClient(
            @PathVariable String email,
            @Valid @RequestBody Client clientUpdate) {
        log.info("Updating client with email: {}", email);
        
        return clientService.updateClient(email, clientUpdate)
                .map(updatedClient -> {
                    log.info("Client updated successfully: {}", email);
                    return ResponseEntity.ok(updatedClient);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteClient(@PathVariable String email) {
        log.info("Deleting client with email: {}", email);
        
        boolean deleted = clientService.deleteClientByEmail(email);
        if (deleted) {
            log.info("Client deleted successfully: {}", email);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Client not found for deletion: {}", email);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.email());
        
        try {
            Client client = clientService.authenticate(
                loginRequest.email(),
                loginRequest.password()
            );
            
            String token = jwtUtil.generateToken(client.getEmail());
            
            LoginResponse response = new LoginResponse(
                token,
                client.getId(),
                client.getEmail(),
                client.getName()
            );
            
            log.info("Login successful for email: {}", loginRequest.email());
            return ResponseEntity.ok(response);
            
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for email: {} - {}", loginRequest.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}