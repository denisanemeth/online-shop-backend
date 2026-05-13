package com.onlineshop.backend.repository;

import com.onlineshop.backend.model.Offer;
import com.onlineshop.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByProduct(Product product);
    void deleteByProduct(Product product);
}