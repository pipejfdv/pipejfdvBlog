package com.pipejfdv.Juegos.MCSJuegos.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class Controller {
    @GetMapping("/test")
    public String hello() {
        return "Hello, World!";
    }
}
