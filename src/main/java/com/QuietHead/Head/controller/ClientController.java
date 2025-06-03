package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Clients")
public class ClientController {

    private final ClientService ClientService;

    @Autowired
    public ClientController(ClientService ClientService) {
        this.ClientService = ClientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client Client) {
        Client created = ClientService.saveClient(Client);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Client>> listarClients() {
        List<Client> Clients = ClientService.listarClients();
        return ResponseEntity.ok(Clients);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Client> seachClientByEmail(@PathVariable String email) {
        Client Client = ClientService.seachByEmail(email);
        if (Client != null) {
            return ResponseEntity.ok(Client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Client> updateClientPorEmail(@PathVariable String email, @RequestBody Client ClientUpdate) {
        Client ClientUpdateBanco = ClientService.updateByEmail(email, ClientUpdate);
        if (ClientUpdateBanco != null) {
            return ResponseEntity.ok(ClientUpdateBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/delete/{email}")
        public ResponseEntity<Void> deleteClientPorEmail(@PathVariable String email) {
        boolean deleted = ClientService.deletePorEmail(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
}