package com.process.api.application.usecase;

import com.process.api.infrastructure.persistence.entity.User;
import com.process.api.infrastructure.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User execute(String email, String password) {

        //  verifica se já existe
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new RuntimeException("User already exists");
        });

        //  cria usuário
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}