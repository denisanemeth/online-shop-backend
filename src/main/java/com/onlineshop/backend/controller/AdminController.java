package com.onlineshop.backend.controller;

import com.onlineshop.backend.model.User;
import com.onlineshop.backend.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/approveSeller/{id}")
    public String approveSeller(@PathVariable Long id) {
        return adminService.approveSeller(id);
    }

    @PutMapping("/deactivateSeller/{id}")
    public String deactivateSeller(@PathVariable Long id) {
        return adminService.deactivateSeller(id);
    }

    @GetMapping("/sellers")
    public List<User> getAllSellers() {
        return adminService.getAllSellers();
    }
}