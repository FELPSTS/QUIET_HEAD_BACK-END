package com.QuietHead.Head.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceQuiet {
    
    public String helloWorld(String nome) {
        return "Olá, " + nome + "!";
    }

    public String helloWorldSafe(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Olá, visitante!";
        }
        return "Olá, " + nome + "!";
    }
}