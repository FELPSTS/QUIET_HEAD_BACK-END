package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Auto;
import com.QuietHead.Head.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    @Autowired
    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public Auto save(Auto auto) {
        return autoRepository.save(auto);
    }

    public List<Auto> listar() {
        return autoRepository.findAll();
    }

    public Auto buscarPorId(Long id) {
        return autoRepository.findById(id).orElse(null);
    }

    public Auto atualizar(Long id, Auto autoAtualizado) {
        Auto existente = autoRepository.findById(id).orElse(null);
        if (existente != null) {
            autoAtualizado.setId(id); 
            return autoRepository.save(autoAtualizado);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (autoRepository.existsById(id)) {
            autoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
