package com.onlineshop.backend.model;

import com.onlineshop.backend.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "offers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Column(nullable = false)
    private Double offeredPrice;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;  // PENDING / APPROVED / REJECTED
}