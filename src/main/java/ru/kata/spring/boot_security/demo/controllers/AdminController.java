package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.Role;
import ru.kata.spring.boot_security.demo.User;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {


    @RequestMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String index(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        Role role = new Role();
        for (Role role1 : user.getRoles()) {
            System.out.println(role1);
            role = role1;
        }
        model.addAttribute("user", role);
        return "index";
    }

}
