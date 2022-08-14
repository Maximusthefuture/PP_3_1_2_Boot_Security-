package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.Role;
import ru.kata.spring.boot_security.demo.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class InitialController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    String createAdminIfNotExists() {
        List<User> userList = userService.listUsers();
        Role role = new Role("ADMIN");
        Role userRole = new Role("USER");
        if (userList.isEmpty()) {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode("admin");
            String userPassword = encoder.encode("user");
            User admin = new User("admin", "Adminov", "admin@mail.ru",(byte) 30, password);
            User user = new User("user", "Userov", "user@mail.ru", (byte) 30, userPassword);
            roleService.add(role);
            roleService.add(userRole);
            admin.addRole(role);
            admin.addRole(userRole);
            user.addRole(userRole);
            userService.add(admin);
            userService.add(user);
        }
        return "index";
    }
}
