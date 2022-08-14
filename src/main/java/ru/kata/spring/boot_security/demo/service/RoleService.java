package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.Role;

import java.util.List;

public interface RoleService {
    void add(Role role);
    List<Role> listRoles();
    void update(Role role);
}
