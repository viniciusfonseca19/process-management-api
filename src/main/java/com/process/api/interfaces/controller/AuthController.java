package com.process.api.interfaces.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login() {
        return "Login não implementado ainda";
    }
}