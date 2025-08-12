package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.exception.AuthenticationException;
import com.QuietHead.Head.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client createClient(Client client) {
        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (client.getPassword() == null || client.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }

        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
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
                if (clientUpdate.getName() != null) {
                    existingClient.setName(clientUpdate.getName());
                }
                if (clientUpdate.getPassword() != null) {
                    existingClient.setPassword(passwordEncoder.encode(clientUpdate.getPassword()));
                }
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

    public Client authenticate(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios");
        }

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Credenciais inválidas"));

        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new AuthenticationException("Credenciais inválidas");
        }

        return client;
    }
}