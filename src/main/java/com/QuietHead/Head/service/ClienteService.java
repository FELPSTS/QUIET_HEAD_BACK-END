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

    public Cliente saveCliente(Cliente cliente) {
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

    public Cliente seachByEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }

    public Cliente updateByEmail(String email, Cliente clienteUpdate) {
        Optional<Cliente> clienteExisting = clienteRepository.findByEmail(email);
        if (clienteExisting .isPresent()) {
            Cliente cliente = clienteExisting.get();
            cliente.setNome(clienteUpdate.getNome());

            return clienteRepository.save(cliente);
        } else {
            return null;
        }
    }

    public boolean deletePorEmail(String email) {
        Optional<Cliente> clienteExisting = clienteRepository.findByEmail(email);
        if (clienteExisting.isPresent()) {
            clienteRepository.delete(clienteExisting.get());
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