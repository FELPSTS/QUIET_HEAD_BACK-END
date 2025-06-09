package com.QuietHead.Head.controller;

import com.QuietHead.Head.dto.LoginRequest;
import com.QuietHead.Head.domain.Administrator;
import com.QuietHead.Head.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrator")

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
    public ResponseEntity<List<Administrator>> listAdministrator() {
        List<Administrator> administrators = administratorService.listAdministrator();
        return ResponseEntity.ok(administrators);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Administrator> seachADMByEmail(@PathVariable String email) {
        Administrator administrator = administratorService.seachByEmail(email);
        if (administrator != null) {
            return ResponseEntity.ok(administrator);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Administrator> updateADMFByEmail(@PathVariable String email, @RequestBody Administrator administratorUpdate) {
        Administrator administratorUpdateBanco = administratorService.updateByEmail(email, administratorUpdate);
        if (administratorUpdateBanco != null) {
            return ResponseEntity.ok(administratorUpdateBanco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
        @DeleteMapping("/delete/{email}")
        public ResponseEntity<Void> deleteADMByEmail(@PathVariable String email) {
        boolean deleted = administratorService.deleteByEmail(email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = administratorService.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());

        if (isValid) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }
}
