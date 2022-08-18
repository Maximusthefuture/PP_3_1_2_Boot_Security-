package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChoiceController {

    @GetMapping("/admin")
    public String admin() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
