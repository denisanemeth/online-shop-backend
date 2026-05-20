package com.onlineshop.backend.service;

import com.onlineshop.backend.enums.Role;
import com.onlineshop.backend.model.User;
import com.onlineshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String approveSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId).orElse(null);
        if (seller == null) return "Seller not found!";

        if (seller.getRole() != Role.SELLER)
            return "User is not a seller!";

        seller.setApproved(true);
        userRepository.save(seller);
        return "Seller approved successfully!";
    }

    public String deactivateSeller(Long sellerId) {
        User seller = userRepository.findById(sellerId).orElse(null);
        if (seller == null) return "Seller not found!";

        if (seller.getRole() != Role.SELLER)
            return "User is not a seller!";

        seller.setActive(false); // contul rămâne în DB dar nu se poate loga
        userRepository.save(seller);
        return "Seller deactivated successfully!";
    }

    public List<User> getAllSellers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.SELLER)
                .toList();
    }
}
