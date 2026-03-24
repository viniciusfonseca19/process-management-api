package com.process.api.interfaces.controller;

import com.process.api.application.dto.request.AuthRequest;
import com.process.api.application.usecase.AuthenticateUserUseCase;
import com.process.api.application.usecase.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
                registerUserUseCase.execute(request.getEmail(), request.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authenticateUserUseCase.execute(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(token);
    }
}