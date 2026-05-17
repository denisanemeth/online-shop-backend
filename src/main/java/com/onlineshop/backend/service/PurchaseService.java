package com.onlineshop.backend.service;

import com.onlineshop.backend.enums.OfferStatus;
import com.onlineshop.backend.model.*;
import com.onlineshop.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final SaleHistoryRepository saleHistoryRepository;

    public PurchaseService(ProductRepository productRepository,
                           UserRepository userRepository,
                           OfferRepository offerRepository,
                           SaleHistoryRepository saleHistoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.saleHistoryRepository = saleHistoryRepository;
    }

    @Transactional
    public String purchase(Long productId, String buyerEmail) {
        // 1. caută produsul
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return "Product not found!";

        // 2. caută buyer-ul
        User buyer = userRepository.findByEmail(buyerEmail).orElse(null);
        if (buyer == null) return "Buyer not found!";

        double finalPrice = product.getPrice();

        // 3. dacă produsul e negociabil, verifică dacă are ofertă aprobată
        if (product.getNegotiable()) {
            Offer approvedOffer = offerRepository.findByProduct(product)
                    .stream()
                    .filter(o -> o.getBuyer().getEmail().equals(buyerEmail)
                            && o.getStatus() == OfferStatus.APPROVED)
                    .findFirst()
                    .orElse(null);

            if (approvedOffer == null) {
                return "No approved offer found for this product!";
            }

            finalPrice = approvedOffer.getOfferedPrice();
        }

        // 4. salvează în SaleHistory
        SaleHistory history = SaleHistory.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .finalPrice(finalPrice)
                .sellerEmail(product.getSeller().getEmail())
                .buyerEmail(buyerEmail)
                .soldAt(LocalDateTime.now())
                .build();
        saleHistoryRepository.save(history);

        // 5. șterge ofertele produsului
        offerRepository.deleteByProduct(product);

        // 6. șterge produsul
        productRepository.delete(product);

        return "Purchase successful! Final price: " + finalPrice;
    }

    public List<SaleHistory> getHistory() {
        return saleHistoryRepository.findAll();
    }
}