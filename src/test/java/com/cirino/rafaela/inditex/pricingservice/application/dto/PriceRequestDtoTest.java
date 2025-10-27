package com.cirino.rafaela.inditex.pricingservice.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceRequestDtoTest {

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        PriceRequestDto dto = new PriceRequestDto();
        dto.setApplicationDate(date);
        dto.setProductId(productId);
        dto.setBrandId(brandId);

        assertEquals(date, dto.getApplicationDate());
        assertEquals(productId, dto.getProductId());
        assertEquals(brandId, dto.getBrandId());
    }
}
