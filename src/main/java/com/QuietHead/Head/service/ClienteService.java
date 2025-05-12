package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.repository.ClienteRepository;
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

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }
}
