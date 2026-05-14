package com.onlineshop.backend.controller;

import com.onlineshop.backend.dto.LoginRequest;
import com.onlineshop.backend.dto.RegisterRequest;
import com.onlineshop.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registerBuyer")
    public String registerBuyer(@RequestBody RegisterRequest request) {
        return authService.registerBuyer(request);
    }

    @PostMapping("/registerSeller")
    public String registerSeller(@RequestBody RegisterRequest request) {
        return authService.registerSeller(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}