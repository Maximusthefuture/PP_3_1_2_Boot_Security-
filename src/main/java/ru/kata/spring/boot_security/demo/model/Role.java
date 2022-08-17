package ru.kata.spring.boot_security.demo.model;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(name = "role")
    private String name;
    public Role(String role) {
        this.name = role;
    }

    public Role() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
