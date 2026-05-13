package com.onlineshop.backend.repository;

import com.onlineshop.backend.model.Product;
import com.onlineshop.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(User seller);
}