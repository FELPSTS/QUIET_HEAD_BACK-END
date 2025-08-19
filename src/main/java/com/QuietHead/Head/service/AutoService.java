package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Auto;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.repository.AutoRepository;
import com.QuietHead.Head.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AutoService {

    private final AutoRepository autoRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Auto save(Auto auto) {
        log.info("Saving auto: {}", auto.getModelo());
        Auto savedAuto = autoRepository.save(auto);
        log.info("Auto saved successfully with ID: {}", savedAuto.getId());
        return savedAuto;
    }

    public List<Auto> list() {
        log.debug("Fetching all autos");
        List<Auto> autos = autoRepository.findAll();
        log.debug("Found {} autos", autos.size());
        return autos;
    }

    public Auto searchById(Long id) {
        log.debug("Searching auto by ID: {}", id);
        Auto auto = autoRepository.findById(id).orElse(null);
        if (auto != null) {
            log.debug("Auto found with ID: {}", id);
        } else {
            log.debug("Auto not found with ID: {}", id);
        }
        return auto;
    }

    @Transactional
    public Auto update(Long id, Auto autoAtualizado) {
        log.info("Updating auto with ID: {}", id);
        
        Auto existente = autoRepository.findById(id).orElse(null);
        if (existente != null) {
            autoAtualizado.setId(id); 
            Auto updatedAuto = autoRepository.save(autoAtualizado);
            log.info("Auto updated successfully with ID: {}", id);
            return updatedAuto;
        }
        
        log.warn("Auto not found for update with ID: {}", id);
        return null;
    }

    @Transactional
    public boolean deletar(Long id) {
        log.info("Deleting auto with ID: {}", id);
        
        if (autoRepository.existsById(id)) {
            autoRepository.deleteById(id);
            log.info("Auto deleted successfully with ID: {}", id);
            return true;
        }
        
        log.warn("Auto not found for deletion with ID: {}", id);
        return false;
    }

    @Transactional
    public Auto linkOwner(Long autoId, Long clientId) {
        log.info("Linking owner {} to auto {}", clientId, autoId);
        
        Auto auto = autoRepository.findById(autoId).orElse(null);
        Client client = clientRepository.findById(clientId).orElse(null);

        if (auto == null || client == null) {
            log.warn("Failed to link owner - auto ID: {} or client ID: {} not found", autoId, clientId);
            return null; 
        }

        auto.setOwner(List.of(client));
        Auto updatedAuto = autoRepository.save(auto);
        log.info("Owner {} linked successfully to auto {}", clientId, autoId);
        return updatedAuto;
    }
}
