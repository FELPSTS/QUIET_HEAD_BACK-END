package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Auto;
import com.QuietHead.Head.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Auto>> listarAutos() {
        List<Auto> autos = autoService.listar();
        return ResponseEntity.ok(autos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auto> buscarAutoPorId(@PathVariable Long id) {
        Auto auto = autoService.buscarPorId(id);
        if (auto != null) {
            return ResponseEntity.ok(auto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Auto> atualizarAuto(@PathVariable Long id, @RequestBody Auto autoAtualizado) {
        Auto atualizado = autoService.atualizar(id, autoAtualizado);
        if (atualizado != null) {
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarAuto(@PathVariable Long id) {
        boolean deletado = autoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
