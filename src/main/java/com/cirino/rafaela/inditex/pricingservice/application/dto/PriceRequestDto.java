package com.cirino.rafaela.inditex.pricingservice.application.dto;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class PriceRequestDto {

    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
