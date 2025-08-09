package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.repository.ClientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> updateClient(String email, Client clientUpdate) {
        return clientRepository.findByEmail(email)
            .map(existingClient -> {
                existingClient.setName(clientUpdate.getName());
                return clientRepository.save(existingClient);
            });
    }

    public boolean deleteClientByEmail(String email) {
        return clientRepository.findByEmail(email)
            .map(client -> {
                clientRepository.delete(client);
                return true;
            })
            .orElse(false);
    }

    @Transactional(readOnly = true)
    public boolean validateCredentials(String email, String password) {
        return clientRepository.findByEmail(email)
            .map(client -> client.getPassword().equals(password))
            .orElse(false);
    }

    @PostConstruct
    public void verifyDatabaseConnection() {
        try {
            System.out.println("Database connection test: " + 
                (clientRepository.count() >= 0 ? "✅ Successful" : "❌ Failed"));
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}