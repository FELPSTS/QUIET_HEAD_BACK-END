package com.QuietHead.Head.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.QuietHead.Head.service.ServiceQuiet;

@RestController
public class MappingController {

    @Autowired
    private final ServiceQuiet helloWorldService;

    public MappingController(ServiceQuiet helloWorldService){
        this.helloWorldService = helloWorldService;
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
