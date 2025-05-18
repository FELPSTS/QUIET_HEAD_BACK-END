package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.repository.ClienteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ServiceQuiet serviceQuiet;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ServiceQuiet serviceQuiet) {
        this.clienteRepository = clienteRepository;
        this.serviceQuiet = serviceQuiet;
    }

    public Cliente salvarCliente(Cliente cliente) {
        String saudacao = serviceQuiet.helloWorld(cliente.getNome());
        System.out.println(saudacao);

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }

    public Cliente alterarPorId(Long id, Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            return clienteRepository.save(cliente);
        } else {
            return null;
        }
    }

    public Cliente alterarPorEmail(String email, Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(email);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            return clienteRepository.save(cliente);
        } else {
            return null;
        }
    }

    public boolean deletarPorEmail(String email) {
        Optional<Cliente> clienteExistente = clienteRepository.findByEmail(email);
        if (clienteExistente.isPresent()) {
            clienteRepository.delete(clienteExistente.get());
            return true;
        } else {
            return false;
        }
    }
      @PostConstruct
    public void testarConexaoNeo4j() {
        try {
            List<Cliente> clientes = clienteRepository.findAll();
            System.out.println("✅ Conexão com o Neo4j funcionando. Total de clientes: " + clientes.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o Neo4j: " + e.getMessage());
        }
    }
}