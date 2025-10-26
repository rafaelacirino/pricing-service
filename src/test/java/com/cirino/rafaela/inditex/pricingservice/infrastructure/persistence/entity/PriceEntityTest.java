package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceEntityTest {

    @Test
    void testSettersAndGetters() {
        PriceEntity price = new PriceEntity();

        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 10, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        price.setId(1L);
        price.setBrandId(1L);
        price.setBrandName("ZARA");
        price.setStartDate(start);
        price.setEndDate(end);
        price.setPriceList(1);
        price.setProductId(35455L);
        price.setPriority(0);
        price.setPrice(new BigDecimal("35.50"));
        price.setCurrency("EUR");

        assertEquals(1L, price.getId());
        assertEquals(1L, price.getBrandId());
        assertEquals("ZARA", price.getBrandName());
        assertEquals(start, price.getStartDate());
        assertEquals(end, price.getEndDate());
        assertEquals(1, price.getPriceList());
        assertEquals(35455L, price.getProductId());
        assertEquals(0, price.getPriority());
        assertEquals(new BigDecimal("35.50"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
    }
}
