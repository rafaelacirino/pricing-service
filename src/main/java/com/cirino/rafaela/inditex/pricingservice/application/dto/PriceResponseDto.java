package com.cirino.rafaela.inditex.pricingservice.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PriceResponseDto {

    private Long productId;
    private Long brandId;
    private String  brandName;
    private BigDecimal price;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String currency;
}
