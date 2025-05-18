package com.QuietHead.Head.controller;
import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.service.ClienteService;
import org.apache.catalina.connector.Response;
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
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente criado = clienteService.salvarCliente(cliente);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Cliente> buscarClientePorEmail(@PathVariable String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atualizar/{email}")
    public ResponseEntity<Cliente> atualizarClientePorEmail(@PathVariable String email, @RequestBody Cliente clienteAtualizado) {
        Cliente clienteAtualizadoBanco = clienteService.alterarPorEmail(email, clienteAtualizado);
        if (clienteAtualizadoBanco != null) {
            return ResponseEntity.ok(clienteAtualizadoBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/delete/{email}")
        public ResponseEntity<Void> deletarClientePorEmail(@PathVariable String email) {
        boolean deletado = clienteService.deletarPorEmail(email);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
    
}