package com.onlineshop.backend.service;

import com.onlineshop.backend.dto.OfferRequest;
import com.onlineshop.backend.enums.OfferStatus;
import com.onlineshop.backend.model.Offer;
import com.onlineshop.backend.model.Product;
import com.onlineshop.backend.model.User;
import com.onlineshop.backend.repository.OfferRepository;
import com.onlineshop.backend.repository.ProductRepository;
import com.onlineshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OfferService(OfferRepository offerRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String makeOffer(OfferRequest request) {
        // 1. caută produsul
        Product product = productRepository.findById(request.getProductId())
                .orElse(null);
        if (product == null) return "Product not found!";

        // 2. verifică dacă produsul e negociabil
        if (!product.getNegotiable()) {
            return "This product is not negotiable!";
        }

        // 3. verifică dacă prețul oferit e peste minimumPrice
        if (request.getOfferedPrice() < product.getMinimumPrice()) {
            return "Offer rejected! Price is below minimum accepted price.";
        }

        // 4. caută buyer-ul
        User buyer = userRepository.findByEmail(request.getBuyerEmail())
                .orElse(null);
        if (buyer == null) return "Buyer not found!";

        // 5. salvează oferta
        Offer offer = Offer.builder()
                .product(product)
                .buyer(buyer)
                .offeredPrice(request.getOfferedPrice())
                .status(OfferStatus.PENDING)
                .build();

        offerRepository.save(offer);
        return "Offer submitted successfully!";
    }

    public String approveOffer(Long offerId, String sellerEmail) {
        Offer offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null) return "Offer not found!";

        // verifică dacă seller-ul e proprietarul produsului
        if (!offer.getProduct().getSeller().getEmail().equals(sellerEmail)) {
            return "You can only approve offers for your own products!";
        }

        offer.setStatus(OfferStatus.APPROVED);
        offerRepository.save(offer);
        return "Offer approved!";
    }

    public String rejectOffer(Long offerId, String sellerEmail) {
        Offer offer = offerRepository.findById(offerId).orElse(null);
        if (offer == null) return "Offer not found!";

        // verifică dacă seller-ul e proprietarul produsului
        if (!offer.getProduct().getSeller().getEmail().equals(sellerEmail)) {
            return "You can only reject offers for your own products!";
        }

        offer.setStatus(OfferStatus.REJECTED);
        offerRepository.save(offer);
        return "Offer rejected!";
    }
    public List<Offer> getOffersForSeller(String sellerEmail) {
        User seller = userRepository.findByEmail(sellerEmail).orElse(null);
        if (seller == null) return List.of();
        return offerRepository.findByProductSeller(seller);
    }

    public Offer getOfferForBuyer(Long productId, String buyerEmail) {
        return offerRepository.findByProductIdAndBuyerEmail(productId, buyerEmail)
                .orElse(null);
    }
}