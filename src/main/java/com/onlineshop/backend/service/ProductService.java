package com.onlineshop.backend.service;

import com.onlineshop.backend.dto.ProductRequest;
import com.onlineshop.backend.model.Product;
import com.onlineshop.backend.model.User;
import com.onlineshop.backend.repository.ProductRepository;
import com.onlineshop.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String addProduct(ProductRequest request) {
        // 1. caută seller-ul după email
        User seller = userRepository.findByEmail(request.getSellerEmail())
                .orElse(null);

        if (seller == null) {
            return "Seller not found!";
        }

        // 2. verifică dacă seller-ul e aprobat
        if (!seller.getApproved()) {
            return "Seller not approved!";
        }

        // 3. creează produsul
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .negotiable(request.getNegotiable())
                .minimumPrice(request.getMinimumPrice())
                .seller(seller)
                .build();

        productRepository.save(product);
        return "Product added successfully!";
    }

    public String deleteProduct(Long productId, String sellerEmail) {
        // 1. caută produsul
        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return "Product not found!";
        }

        // 2. verifică dacă seller-ul e proprietarul
        if (!product.getSeller().getEmail().equals(sellerEmail)) {
            return "You can only delete your own products!";
        }

        productRepository.delete(product);
        return "Product deleted successfully!";
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
