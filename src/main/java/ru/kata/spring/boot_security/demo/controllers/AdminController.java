package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.Role;
import ru.kata.spring.boot_security.demo.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping
    public String hello(Authentication authentication, ModelMap model) {
        User user = (User) authentication.getPrincipal();
        Set<Role> roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        List<User> users = userService.listUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(value = "/signup")
    public String showSingInForm(User user, ModelMap model) {
        List<Role> listRoles = roleService.listRoles();
        model.addAttribute("listRoles", listRoles);
        return "add_user";
    }

    @RequestMapping(value = "/adduser")
    public String createUser(@Validated User user, @Validated Role role, BindingResult result, ModelMap model) {
        userService.add(user);
        user.addRole(role);
        model.addAttribute("user", user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/edit")
    public String showUpdateForm(@RequestParam long id, ModelMap modelMap) {
        User user = userService.findById(id).orElseThrow();
        modelMap.addAttribute("user", user);
        return "update";
    }

    @RequestMapping(value = "/update")
    public String updateUser(@RequestParam long id, User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete")
    public String deleteUser(@RequestParam long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
