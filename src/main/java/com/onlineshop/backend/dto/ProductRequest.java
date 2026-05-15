package com.onlineshop.backend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductRequest {
    private String name;
    private Double price;
    private String description;
    private Boolean negotiable;
    private Double minimumPrice; // doar dacă negotiable = true
    private String sellerEmail;  // identificăm seller-ul după email
}