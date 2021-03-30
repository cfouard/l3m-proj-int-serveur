package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/")
    public String root() {
        return "Bienvenue sur votre serveur...";
    }

}
