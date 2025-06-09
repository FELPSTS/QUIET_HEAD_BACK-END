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

    public List<Administrator> listAdministrator() {
        return administratorRepository.findAll();
    }

    public Administrator seachById(Long id) {
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

    public boolean deleteByEmail(String email) {
        Optional<Administrator> administratorExisting = administratorRepository.findByEmail(email);
        if (administratorExisting.isPresent()) {
            administratorRepository.delete(administratorExisting.get());
            return true;
        } else {
            return false;
        }
    }
     public boolean validateLogin(String email, String password) {
        Optional<Administrator> administratorOpt = administratorRepository.findByEmail(email);
        return administratorOpt.isPresent() && administratorOpt.get().getPassword().equals(password);
    }
}
