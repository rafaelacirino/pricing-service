package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.mapper;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PriceMapperTest {

    @Test
    void shouldMapPriceEntityToDomainCorrectly() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 6, 14, 23, 59);

        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1L);
        entity.setBrandName("ZARA");
        entity.setStartDate(start);
        entity.setEndDate(end);
        entity.setPriceList(2);
        entity.setProductId(35455L);
        entity.setPriority(1);
        entity.setPrice(new BigDecimal("25.45"));
        entity.setCurrency("EUR");

        // Act
        Price result = PriceMapper.toDomain(entity);

        // Assert
        assertEquals(1L, result.getBrandId());
        assertEquals("ZARA", result.getBrandName());
        assertEquals(start, result.getStartDate());
        assertEquals(end, result.getEndDate());
        assertEquals(2, result.getPriceList());
        assertEquals(35455L, result.getProductId());
        assertEquals(1, result.getPriority());

        Money money = result.getMoney();
        assertNotNull(money);
        assertEquals(new BigDecimal("25.45"), money.getAmount());
        assertEquals("EUR", money.getCurrency());
    }
}
