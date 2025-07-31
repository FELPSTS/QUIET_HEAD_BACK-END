package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(summary = "Create a new client")
    @ApiResponse(responseCode = "201", description = "Client created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.saveClient(client);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(createdClient.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(createdClient);
    }

    @GetMapping
    @Operation(summary = "Get all clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.listClients());
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get client by email")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<Client> getClientByEmail(
            @Parameter(description = "Email of the client to be searched")
            @PathVariable String email) {
        return clientService.seachByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    @Operation(summary = "Update client by email")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<Client> updateClient(
            @Parameter(description = "Email of the client to be updated")
            @PathVariable String email,
            @RequestBody Client clientUpdate) {
        return clientService.updateByEmail(email, clientUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete client by email")
    @ApiResponse(responseCode = "204", description = "Client deleted successfully")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "Email of the client to be deleted")
            @PathVariable String email) {
        return clientService.deletebyEmail(email) 
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}