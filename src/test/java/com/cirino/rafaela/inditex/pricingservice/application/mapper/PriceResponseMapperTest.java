package com.cirino.rafaela.inditex.pricingservice.application.mapper;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceResponseMapperTest {

    @Test
    void shouldMapPriceToPriceResponseDtoCorrectly() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 18, 30);
        Price price = Price.builder()
                .productId(35455L)
                .brandId(1L)
                .brandName("ZARA")
                .priceList(2)
                .startDate(startDate)
                .endDate(endDate)
                .money(new Money(new BigDecimal("25.45"), "EUR"))
                .priority(1)
                .build();

        // Act
        PriceResponseDto dto = PriceResponseMapper.toDto(price);

        // Assert
        assertEquals(35455L, dto.getProductId());
        assertEquals(1L, dto.getBrandId());
        assertEquals("ZARA", dto.getBrandName());
        assertEquals(2, dto.getPriceList());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
        assertEquals(new BigDecimal("25.45"), dto.getPrice());
        assertEquals("EUR", dto.getCurrency());
    }
}
