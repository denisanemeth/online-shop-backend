package com.onlineshop.backend.controller;

import com.onlineshop.backend.dto.ProductRequest;
import com.onlineshop.backend.model.Product;
import com.onlineshop.backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public String addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @RequestParam String sellerEmail) {
        return productService.deleteProduct(id, sellerEmail);
    }
}