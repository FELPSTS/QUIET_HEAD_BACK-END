package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.repository.ClientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ServiceQuiet serviceQuiet;

    @Autowired
    public ClientService(ClientRepository clientRepository, ServiceQuiet serviceQuiet) {
        this.clientRepository = clientRepository;
        this.serviceQuiet = serviceQuiet;
    }

    public Client saveClient(Client client) {
        String saudacao = serviceQuiet.helloWorld(client.getNome());
        System.out.println(saudacao);

        return clientRepository.save(client);
    }

    public List<Client> listarClients() {
        return clientRepository.findAll();
    }

    public Client buscarPorId(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client seachByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    public Client updateByEmail(String email, Client clientUpdate) {
        Optional<Client> clientExisting = clientRepository.findByEmail(email);
        if (clientExisting.isPresent()) {
            Client client = clientExisting.get();
            client.setNome(clientUpdate.getNome());
            // Adicione outros campos que quiser atualizar aqui
            return clientRepository.save(client);
        } else {
            return null;
        }
    }

    public boolean deletePorEmail(String email) {
        Optional<Client> clientExisting = clientRepository.findByEmail(email);
        if (clientExisting.isPresent()) {
            clientRepository.delete(clientExisting.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean validateLogin(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);
        return clientOpt.isPresent() && clientOpt.get().getPassword().equals(password);
    }

    @PostConstruct
    public void testarConexaoNeo4j() {
        try {
            List<Client> clients = clientRepository.findAll();
            System.out.println("✅ Conexão com o Neo4j funcionando. Total de Clients: " + clients.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o Neo4j: " + e.getMessage());
        }
    }
}
