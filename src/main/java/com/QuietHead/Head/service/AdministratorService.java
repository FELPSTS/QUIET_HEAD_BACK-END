package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.exception.ResourceNotFoundException;
import com.QuietHead.Head.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Transactional
    public Administrator saveAdministrator(Administrator administrator) {
        log.info("Saving administrator with email: {}", administrator.getEmail());
        Administrator savedAdministrator = administratorRepository.save(administrator);
        log.info("Administrator saved successfully with ID: {}", savedAdministrator.getId());
        return savedAdministrator;
    }

    public List<Administrator> listAdministrator() {
        log.debug("Fetching all administrators");
        List<Administrator> administrators = administratorRepository.findAll();
        log.debug("Found {} administrators", administrators.size());
        return administrators;
    }

    public Administrator searchById(Long id) {
        log.debug("Searching administrator by ID: {}", id);
        Administrator administrator = administratorRepository.findById(id)
            .orElseThrow(() -> {
                log.error("Administrator not found with ID: {}", id);
                return new ResourceNotFoundException("Administrator not found with ID: " + id);
            });
        log.debug("Administrator found with ID: {}", id);
        return administrator;
    }

    public Administrator searchByEmail(String email) {
        log.debug("Searching administrator by email: {}", email);
        Administrator administrator = administratorRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.error("Administrator not found with email: {}", email);
                return new ResourceNotFoundException("Administrator not found with email: " + email);
            });
        log.debug("Administrator found with email: {}", email);
        return administrator;
    }

    @Transactional
    public Administrator updateByEmail(String email, Administrator administratorUpdate) {
        log.info("Updating administrator with email: {}", email);
        
        Optional<Administrator> administratorExisting = administratorRepository.findByEmail(email);
        if (administratorExisting.isPresent()) {
            Administrator administrator = administratorExisting.get();
            administrator.setName(administratorUpdate.getName());

            Administrator updatedAdministrator = administratorRepository.save(administrator);
            log.info("Administrator updated successfully with email: {}", email);
            return updatedAdministrator;
        } else {
            log.warn("Administrator not found for update with email: {}", email);
            return null;
        }
    }

    @Transactional
    public boolean deleteByEmail(String email) {
        log.info("Deleting administrator with email: {}", email);
        
        Optional<Administrator> administratorExisting = administratorRepository.findByEmail(email);
        if (administratorExisting.isPresent()) {
            administratorRepository.delete(administratorExisting.get());
            log.info("Administrator deleted successfully with email: {}", email);
            return true;
        } else {
            log.warn("Administrator not found for deletion with email: {}", email);
            return false;
        }
    }
    
    public boolean validateLogin(String email, String password) {
        log.debug("Validating login for email: {}", email);
        
        Optional<Administrator> administratorOpt = administratorRepository.findByEmail(email);
        boolean isValid = administratorOpt.isPresent() && administratorOpt.get().getPassword().equals(password);
        
        if (isValid) {
            log.debug("Login validation successful for email: {}", email);
        } else {
            log.warn("Login validation failed for email: {}", email);
        }
        
        return isValid;
    }
}