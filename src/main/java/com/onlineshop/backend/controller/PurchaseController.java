package com.onlineshop.backend.controller;

import com.onlineshop.backend.model.SaleHistory;
import com.onlineshop.backend.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/purchase/{productId}")
    public String purchase(@PathVariable Long productId,
                           @RequestParam String buyerEmail) {
        return purchaseService.purchase(productId, buyerEmail);
    }

    @GetMapping("/history")
    public List<SaleHistory> getHistory() {
        return purchaseService.getHistory();
    }
}
