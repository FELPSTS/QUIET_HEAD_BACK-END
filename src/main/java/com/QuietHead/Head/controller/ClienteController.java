package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente created = clienteService.saveCliente(cliente);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Cliente> seachClienteByEmail(@PathVariable String email) {
        Cliente cliente = clienteService.seachByEmail(email);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Cliente> updateClientePorEmail(@PathVariable String email, @RequestBody Cliente clienteUpdate) {
        Cliente clienteUpdateBanco = clienteService.updateByEmail(email, clienteUpdate);
        if (clienteUpdateBanco != null) {
            return ResponseEntity.ok(clienteUpdateBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/delete/{email}")
        public ResponseEntity<Void> deleteClientePorEmail(@PathVariable String email) {
        boolean deleted = clienteService.deletePorEmail(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    
}