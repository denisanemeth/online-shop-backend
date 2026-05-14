package com.onlineshop.backend.service;

import com.onlineshop.backend.dto.LoginRequest;
import com.onlineshop.backend.dto.RegisterRequest;
import com.onlineshop.backend.enums.Role;
import com.onlineshop.backend.model.User;
import com.onlineshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerBuyer(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.BUYER)
                .approved(true)
                .active(true)
                .build();

        userRepository.save(user);
        return "Buyer registered successfully!";
    }

    public String registerSeller(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.SELLER)
                .approved(false)
                .active(true)
                .build();

        userRepository.save(user);
        return "Seller registered! Waiting for admin approval.";
    }

    public String login(LoginRequest request) {
        if (request.getEmail().equals("admin@email.com")
                && request.getPassword().equals("admin")) {
            return "Login successful! Role: ADMIN";
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "User not found!";
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return "Wrong password!";
        }

        if (!user.getActive()) {
            return "Account is deactivated!";
        }

        if (user.getRole().name().equals("SELLER") && !user.getApproved()) {
            return "Seller account not approved yet!";
        }

        return "Login successful! Role: " + user.getRole();
    }
}