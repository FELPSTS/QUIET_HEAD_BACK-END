package com.QuietHead.Head.controller;

import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client created = clientService.saveClient(client);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.listarClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        Optional<Client> client = Optional.ofNullable(clientService.seachByEmail(email));
        return client.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Client> updateClientByEmail(@PathVariable String email,
                                                      @RequestBody Client clientUpdate) {
        Optional<Client> updatedClient = Optional.ofNullable(clientService.updateByEmail(email, clientUpdate));
        return updatedClient.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteClientByEmail(@PathVariable String email) {
        boolean deleted = clientService.deletePorEmail(email);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
