package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Auto;
import com.QuietHead.Head.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autos")
public class AutoController {

    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @PostMapping
    public ResponseEntity<Auto> createAuto(@RequestBody Auto auto) {
        Auto created = autoService.save(auto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Auto>> getAllAutos() {
        List<Auto> autos = autoService.list();
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auto> getAutoById(@PathVariable Long id) {
        Optional<Auto> auto = Optional.ofNullable(autoService.seachById(id));
        return auto.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auto> updateAuto(@PathVariable Long id,@RequestBody Auto autoAtualizado) {
        Optional<Auto> atualizado = Optional.ofNullable(autoService.update(id, autoAtualizado));
        return atualizado.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        boolean deleted = autoService.deletar(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @PutMapping("/{autoId}/owner/{clientId}")
    public ResponseEntity<Auto> linkOwnerToAuto(@PathVariable Long autoId,@PathVariable Long clientId) {
        Optional<Auto> updated = Optional.ofNullable(autoService.linkOwner(autoId, clientId));
        return updated.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
}
