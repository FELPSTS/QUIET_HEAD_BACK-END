package com.QuietHead.Head.controller;

import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Clients")
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
    public ResponseEntity<List<Client>> listarClients() {
        List<Client> clients = clientService.listarClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Client> searchClientByEmail(@PathVariable String email) {
        Client client = clientService.seachByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Client> updateClientPorEmail(@PathVariable String email, @RequestBody Client clientUpdate) {
        Client updatedClient = clientService.updateByEmail(email, clientUpdate);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteClientPorEmail(@PathVariable String email) {
        boolean deleted = clientService.deletePorEmail(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = clientService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

        if (isValid) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }
}
