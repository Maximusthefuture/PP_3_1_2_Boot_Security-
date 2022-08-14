package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public void add(User user) {
        repository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> listUsers() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void update(User user) {
        repository.saveAndFlush(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
