package com.QuietHead.Head.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {

    @GetMapping("/")
    public String home() {
        return "Bem-vindo à API QuietHead!";
    }

    @GetMapping("/status")
    public String status() {
        return "API funcionando normalmente!";
    }
}