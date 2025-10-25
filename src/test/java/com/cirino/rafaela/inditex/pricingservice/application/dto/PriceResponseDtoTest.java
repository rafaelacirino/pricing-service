package com.cirino.rafaela.inditex.pricingservice.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceResponseDtoTest {

    @Test
    void shouldCreateDtoWithCorrectValues() {
        Long productId = 35455L;
        Long brandId = 1L;
        String brandName = "ZARA";
        Integer priceList = 2;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        BigDecimal price = new BigDecimal("25.45");
        String currency = "EUR";

        PriceResponseDto dto = PriceResponseDto.builder()
                .productId(productId)
                .brandId(brandId)
                .brandName(brandName)
                .priceList(priceList)
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .currency(currency)
                .build();

        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
        assertEquals(brandName, dto.getBrandName());
        assertEquals(priceList, dto.getPriceList());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(price, dto.getPrice());
        assertEquals(currency, dto.getCurrency());
    }
}
