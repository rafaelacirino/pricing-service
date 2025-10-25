package com.cirino.rafaela.inditex.pricingservice.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PriceRequestDto {

    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
