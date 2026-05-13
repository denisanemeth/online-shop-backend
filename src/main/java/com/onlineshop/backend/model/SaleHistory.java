package com.onlineshop.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SaleHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productDescription;
    private Double finalPrice;

    private String sellerEmail;
    private String buyerEmail;

    private LocalDateTime soldAt;
}