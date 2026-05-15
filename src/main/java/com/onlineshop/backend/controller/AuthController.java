package com.onlineshop.backend.controller;

import com.onlineshop.backend.dto.LoginRequest;
import com.onlineshop.backend.dto.RegisterRequest;
import com.onlineshop.backend.model.User;
import com.onlineshop.backend.repository.UserRepository;
import com.onlineshop.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
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

    @PostMapping("/approveSeller")
    public String approveSeller(@RequestParam String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found!";
        user.setApproved(true);
        userRepository.save(user);
        return "Seller approved!";
    }
}