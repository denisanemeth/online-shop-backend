package com.onlineshop.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    // true = cumpărătorul poate face ofertă
    private Boolean negotiable;


    // Doar dacă negotiable = true; NU se trimite în response către buyer
    @JsonIgnore
    private Double minimumPrice;

    @JsonIgnore
    @ManyToOne // mai multe produse - un seller
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    @Transient  // nu se salvează în DB, e calculat
    public String getSellerEmail() {
        return seller != null ? seller.getEmail() : null;
    }
}