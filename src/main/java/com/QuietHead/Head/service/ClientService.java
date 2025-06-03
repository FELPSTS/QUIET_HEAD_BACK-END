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

    private final ClientRepository ClientRepository;
    private final ServiceQuiet serviceQuiet;

    @Autowired
    public ClientService(ClientRepository ClientRepository, ServiceQuiet serviceQuiet) {
        this.ClientRepository = ClientRepository;
        this.serviceQuiet = serviceQuiet;
    }

    public Client saveClient(Client Client) {
        String saudacao = serviceQuiet.helloWorld(Client.getNome());
        System.out.println(saudacao);

        return ClientRepository.save(Client);
    }

    public List<Client> listarClients() {
        return ClientRepository.findAll();
    }

    public Client buscarPorId(Long id) {
        return ClientRepository.findById(id).orElse(null);
    }

    public Client seachByEmail(String email) {
        return ClientRepository.findByEmail(email).orElse(null);
    }

    public Client updateByEmail(String email, Client ClientUpdate) {
        Optional<Client> ClientExisting = ClientRepository.findByEmail(email);
        if (ClientExisting .isPresent()) {
            Client Client = ClientExisting.get();
            Client.setNome(ClientUpdate.getNome());

            return ClientRepository.save(Client);
        } else {
            return null;
        }
    }

    public boolean deletePorEmail(String email) {
        Optional<Client> ClientExisting = ClientRepository.findByEmail(email);
        if (ClientExisting.isPresent()) {
            ClientRepository.delete(ClientExisting.get());
            return true;
        } else {
            return false;
        }
    }
      @PostConstruct
    public void testarConexaoNeo4j() {
        try {
            List<Client> Clients = ClientRepository.findAll();
            System.out.println("✅ Conexão com o Neo4j funcionando. Total de Clients: " + Clients.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o Neo4j: " + e.getMessage());
        }
    }
}