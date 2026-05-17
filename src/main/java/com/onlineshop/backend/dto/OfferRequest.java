package com.onlineshop.backend.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OfferRequest {
    private Long productId;
    private String buyerEmail;
    private Double offeredPrice;
}