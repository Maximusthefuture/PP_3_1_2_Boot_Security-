package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    void delete(long id);
    List<User> listUsers();
    void update(User user);
    Optional<User> findById(long id);
    User findByName(String name);
}
