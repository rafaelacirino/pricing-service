package com.cirino.rafaela.inditex.pricingservice.application.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class PriceResponseDto {

    private Long productId;
    private Long brandId;
    private String  brandName;
    private BigDecimal price;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String currency;

    public PriceResponseDto(Long productId, Long brandId, String brandName, Integer priceList, LocalDateTime startDate,
                            LocalDateTime endDate, BigDecimal price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }
}
