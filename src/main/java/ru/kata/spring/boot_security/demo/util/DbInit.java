package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DbInit {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostConstruct
    void createAdminIfNotExists() {
        List<User> list = userService.listUsers();
        if (list.isEmpty()) {
            Role role = new Role("ADMIN");
            Role userRole = new Role("USER");
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode("admin");
            String userPassword = encoder.encode("user");
            User admin = new User("admin", "Adminov", "admin@mail.ru", (byte) 30, password);
            User user = new User("user", "Userov", "user@mail.ru", (byte) 30, userPassword);
            roleService.add(role);
            roleService.add(userRole);
            admin.addRole(role);
            admin.addRole(userRole);
            user.addRole(userRole);
            userService.add(admin);
            userService.add(user);
        }
    }
}
