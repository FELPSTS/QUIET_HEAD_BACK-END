package com.QuietHead.Head.controller;
import java.security.Provider.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.service.ClientService;
import com.QuietHead.Head.service.ServiceQuiet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class QuietController {

    private final ServiceQuiet helloWorldService;
    private final ClientRepository ClientRepository;

@Autowired
    public QuietController(ServiceQuiet helloWorldService, ClientRepository ClientRepository) {
        this.helloWorldService = helloWorldService;
        this.ClientRepository = ClientRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Bem-vindo à API QuietHead!";
    }

    @GetMapping("/status")
    public String status() {
        return "API funcionando normalmente!";
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return helloWorldService.helloWorld(" QUIET");
    }
}
