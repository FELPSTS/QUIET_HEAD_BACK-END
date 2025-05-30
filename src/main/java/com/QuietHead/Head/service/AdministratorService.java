package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.repository.AdministratorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository, ServiceQuiet serviceQuiet) {
        this.administratorRepository = administratorRepository;
    }

    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public List<Administrator> listarAdministrator() {
        return administratorRepository.findAll();
    }

    public Administrator buscarPorId(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    public Administrator seachByEmail(String email) {
        return administratorRepository.findByEmail(email).orElse(null);
    }

    public Administrator updateByEmail(String email, Administrator AdministratorUpdate) {
        Optional<Administrator> administratorExisting = administratorRepository.findByEmail(email);
        if (administratorExisting.isPresent()) {
            Administrator administrator = administratorExisting.get();
            administrator.setNome(AdministratorUpdate.getNome());

            return administratorRepository.save(administrator);
        } else {
            return null;
        }
    }

    public boolean deletePorEmail(String email) {
        Optional<Administrator> administratorExisting = administratorRepository.findByEmail(email);
        if (administratorExisting.isPresent()) {
            administratorRepository.delete(administratorExisting.get());
            return true;
        } else {
            return false;
        }
    }
      @PostConstruct
    public void testarConexaoNeo4j() {
        try {
            List<Administrator> administrators = administratorRepository.findAll();
            System.out.println("✅ Conexão com o Neo4j funcionando. Total de clientes: " + administrators.size());
        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar com o Neo4j: " + e.getMessage());
        }
    }
}