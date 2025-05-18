package com.QuietHead.Head.controller;
import java.security.Provider.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.QuietHead.Head.domain.Cliente;
import com.QuietHead.Head.repository.ClienteRepository;
import com.QuietHead.Head.service.ClienteService;
import com.QuietHead.Head.service.ServiceQuiet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class QuietController {

    private final ServiceQuiet helloWorldService;
    private final ClienteRepository clienteRepository;

@Autowired
    public QuietController(ServiceQuiet helloWorldService, ClienteRepository clienteRepository) {
        this.helloWorldService = helloWorldService;
        this.clienteRepository = clienteRepository;
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
