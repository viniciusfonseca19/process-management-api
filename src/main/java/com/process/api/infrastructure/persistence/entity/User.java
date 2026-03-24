package com.process.api.infrastructure.persistence.entity;

import jakarta.persistence.Entity;

@Entity
public class User {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}