package com.cirino.rafaela.inditex.pricingservice.domain.entity;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    void shouldCreatePriceWithCorrectValuesUsingBuilder() {
        Long brandId = 1L;
        String brandName = "ZARA";
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 22, 0);
        Integer priceList = 2;
        Long productId = 35455L;
        Integer priority = 1;
        Money money = new Money(new BigDecimal("25.45"), "EUR");

        Price price = Price.builder()
                .brandId(brandId)
                .brandName(brandName)
                .startDate(startDate)
                .endDate(endDate)
                .priceList(priceList)
                .productId(productId)
                .priority(priority)
                .money(money)
                .build();

        assertEquals(brandId, price.getBrandId());
        assertEquals(brandName, price.getBrandName());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(priceList, price.getPriceList());
        assertEquals(productId, price.getProductId());
        assertEquals(priority, price.getPriority());
        assertEquals(money, price.getMoney());
        assertEquals(new BigDecimal("25.45"), price.getMoney().getAmount());
        assertEquals("EUR", price.getMoney().getCurrency());
    }
}
