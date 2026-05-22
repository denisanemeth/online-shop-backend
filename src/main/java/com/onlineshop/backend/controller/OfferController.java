package com.onlineshop.backend.controller;

import com.onlineshop.backend.dto.OfferRequest;
import com.onlineshop.backend.model.Offer;
import com.onlineshop.backend.service.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<Offer> getOffersForSeller(@RequestParam String sellerEmail) {
        return offerService.getOffersForSeller(sellerEmail);
    }

    @PostMapping
    public String makeOffer(@RequestBody OfferRequest request) {
        return offerService.makeOffer(request);
    }

    @PutMapping("/{id}/approve")
    public String approveOffer(@PathVariable Long id,
                               @RequestParam String sellerEmail) {
        return offerService.approveOffer(id, sellerEmail);
    }

    @PutMapping("/{id}/reject")
    public String rejectOffer(@PathVariable Long id,
                              @RequestParam String sellerEmail) {
        return offerService.rejectOffer(id, sellerEmail);
    }
}