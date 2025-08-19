package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.service.AdministratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/administrators")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PostMapping
    public ResponseEntity<Administrator> createAdministrator(@Valid @RequestBody Administrator administrator) {
        log.info("Creating administrator with email: {}", administrator.getEmail());
        
        Administrator createdAdministrator = administratorService.saveAdministrator(administrator);
        
        log.info("Administrator created successfully: {}", createdAdministrator.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdministrator);
    }

    @GetMapping
    public ResponseEntity<List<Administrator>> getAllAdministrators() {
        log.debug("Fetching all administrators");
        List<Administrator> administrators = administratorService.listAdministrator();
        return ResponseEntity.ok(administrators);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Administrator> getAdministratorByEmail(@PathVariable String email) {
        log.debug("Fetching administrator by email: {}", email);
        
        return Optional.ofNullable(administratorService.searchByEmail(email))
                .map(administrator -> {
                    log.debug("Administrator found: {}", email);
                    return ResponseEntity.ok(administrator);
                })
                .orElseGet(() -> {
                    log.warn("Administrator not found with email: {}", email);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{email}")
    public ResponseEntity<Administrator> updateAdministratorByEmail(
            @PathVariable String email,
            @Valid @RequestBody Administrator administratorUpdate) {
        log.info("Updating administrator with email: {}", email);
        
        return Optional.ofNullable(administratorService.updateByEmail(email, administratorUpdate))
                .map(updatedAdministrator -> {
                    log.info("Administrator updated successfully: {}", email);
                    return ResponseEntity.ok(updatedAdministrator);
                })
                .orElseGet(() -> {
                    log.warn("Administrator not found for update with email: {}", email);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteAdministratorByEmail(@PathVariable String email) {
        log.info("Deleting administrator with email: {}", email);
        
        boolean deleted = administratorService.deleteByEmail(email);
        if (deleted) {
            log.info("Administrator deleted successfully: {}", email);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Administrator not found for deletion with email: {}", email);
            return ResponseEntity.notFound().build();
        }
    }
}