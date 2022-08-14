package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesRepository repository;

    @Transactional
    @Override
    public void add(Role role) {
        repository.saveAndFlush(role);
    }

    @Override
    public List<Role> listRoles() {
        return repository.findAll();
    }


    @Transactional
    @Override
    public void update(Role role) {
        repository.saveAndFlush(role);
    }
}
