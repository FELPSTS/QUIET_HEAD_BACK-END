package com.QuietHead.Head.controller;

import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping
    public ResponseEntity<Administrator> createAdministrator(@RequestBody Administrator administrator) {
        Administrator created = administratorService.saveAdministrator(administrator);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Administrator>> getAllAdministrators() {
        List<Administrator> administrators = administratorService.listAdministrator();
        return ResponseEntity.ok(administrators);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Administrator> getAdministratorByEmail(@PathVariable String email) {
        Optional<Administrator> administrator = Optional.ofNullable(administratorService.seachByEmail(email));
        return administrator.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<Administrator> updateAdministratorByEmail(@PathVariable String email,
                                                                     @RequestBody Administrator administratorUpdate) {
        Optional<Administrator> updated = Optional.ofNullable(administratorService.updateByEmail(email, administratorUpdate));
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteAdministratorByEmail(@PathVariable String email) {
        boolean deleted = administratorService.deleteByEmail(email);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
