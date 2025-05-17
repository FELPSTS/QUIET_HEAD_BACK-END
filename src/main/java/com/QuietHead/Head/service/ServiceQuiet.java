package com.QuietHead.Head.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceQuiet {


    
    public String helloWorld(String nome) {
        return "Olá, " + nome + "!";
    }
}
