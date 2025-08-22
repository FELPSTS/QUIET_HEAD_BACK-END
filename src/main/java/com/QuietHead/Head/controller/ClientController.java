package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.dto.LoginResponse;
import com.QuietHead.Head.security.JwtUtil;
import com.QuietHead.Head.service.ClientService;
import com.QuietHead.Head.exception.AuthenticationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Client Management", description = "APIs for managing clients and authentication")
public class ClientController {

    private final ClientService clientService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Create a new client", description = "Registers a new client in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Client already exists")
    })
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        log.info("Creating client with email: {}", client.getEmail());
        
        Client createdClient = clientService.createClient(client);
        
        log.info("Client created successfully: {}", createdClient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @Operation(summary = "Get all clients", description = "Retrieves a list of all registered clients")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clients")
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        log.debug("Fetching all clients");
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Get client by ID", description = "Retrieves a specific client by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(
            @Parameter(description = "ID of the client to be retrieved") @PathVariable Long id) {
        log.debug("Fetching client by ID: {}", id);
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get client by email", description = "Retrieves a specific client by their email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client found",
                    content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(
            @Parameter(description = "Email address of the client to be retrieved") @PathVariable String email) {
        log.debug("Fetching client by email: {}", email);
        return clientService.getClientByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update client", description = "Updates an existing client's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client updated successfully",
                    content = @Content(schema = @Schema(implementation = Client.class))),
        @ApiResponse(responseCode = "404", description = "Client not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{email}")
    public ResponseEntity<Client> updateClient(
            @Parameter(description = "Email address of the client to be updated") @PathVariable String email,
            @Valid @RequestBody Client clientUpdate) {
        log.info("Updating client with email: {}", email);
        
        return clientService.updateClient(email, clientUpdate)
                .map(updatedClient -> {
                    log.info("Client updated successfully: {}", email);
                    return ResponseEntity.ok(updatedClient);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete client", description = "Deletes a client from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Email address of the client to be deleted") @PathVariable String email) {
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

    @Operation(summary = "Client login", description = "Authenticates a client and returns a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
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