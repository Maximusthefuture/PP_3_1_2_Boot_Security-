package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public List<User> index(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
//        model.addAttribute("user", userService.findByName(userDetails.getUsername()));
//        model.addAttribute("roles", roleService.listRoles());
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return users;
    }

    @GetMapping(value = "/signup")
    public String showSingInForm(User user, ModelMap model) {
        List<Role> listRoles = roleService.listRoles();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("user", user);
        return "signup";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ("id") Long id) {
        return  ResponseEntity.ok(userService.findById(id).orElseThrow());
    }

    @PostMapping(value = "/adduser")
    public ResponseEntity<User> createUser(@RequestBody User user, ModelMap model) {

//        user.addRole(role);
        model.addAttribute("user", user);
        return ResponseEntity.ok(userService.add(user));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable ("id") Long id, @RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok("");
    }

    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
