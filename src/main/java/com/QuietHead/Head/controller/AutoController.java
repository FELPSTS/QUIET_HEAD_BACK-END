package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Auto;
import com.QuietHead.Head.service.AutoService;
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
@RequestMapping("/autos")
@RequiredArgsConstructor
public class AutoController {

    private final AutoService autoService;

    @PostMapping
    public ResponseEntity<Auto> createAuto(@Valid @RequestBody Auto auto) {
        log.info("Creating auto: {}", auto.getModelo());
        
        Auto createdAuto = autoService.save(auto);
        
        log.info("Auto created successfully with ID: {}", createdAuto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuto);
    }

    @GetMapping
    public ResponseEntity<List<Auto>> getAllAutos() {
        log.debug("Fetching all autos");
        List<Auto> autos = autoService.list();
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auto> getAutoById(@PathVariable Long id) {
        log.debug("Fetching auto by ID: {}", id);
        
        return Optional.ofNullable(autoService.searchById(id))
                .map(auto -> {
                    log.debug("Auto found: {}", auto.getId());
                    return ResponseEntity.ok(auto);
                })
                .orElseGet(() -> {
                    log.warn("Auto not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auto> updateAuto(
            @PathVariable Long id,
            @Valid @RequestBody Auto autoAtualizado) {
        log.info("Updating auto with ID: {}", id);
        
        return Optional.ofNullable(autoService.update(id, autoAtualizado))
                .map(updatedAuto -> {
                    log.info("Auto updated successfully: {}", id);
                    return ResponseEntity.ok(updatedAuto);
                })
                .orElseGet(() -> {
                    log.warn("Auto not found for update with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        log.info("Deleting auto with ID: {}", id);
        
        boolean deleted = autoService.deletar(id);
        if (deleted) {
            log.info("Auto deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Auto not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{autoId}/owner/{clientId}")
    public ResponseEntity<Auto> linkOwnerToAuto(
            @PathVariable Long autoId,
            @PathVariable Long clientId) {
        log.info("Linking owner {} to auto {}", clientId, autoId);
        
        return Optional.ofNullable(autoService.linkOwner(autoId, clientId))
                .map(updatedAuto -> {
                    log.info("Owner linked successfully to auto: {}", autoId);
                    return ResponseEntity.ok(updatedAuto);
                })
                .orElseGet(() -> {
                    log.warn("Failed to link owner {} to auto {}", clientId, autoId);
                    return ResponseEntity.notFound().build();
                });
    }
}