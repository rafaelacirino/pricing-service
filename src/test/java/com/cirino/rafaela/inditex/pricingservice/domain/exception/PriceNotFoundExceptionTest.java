package com.cirino.rafaela.inditex.pricingservice.domain.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithFormattedMessage() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        PriceNotFoundException exception = new PriceNotFoundException(date, productId, brandId);

        String expectedMessage = "Price not found for product 35455, brand 1 at 2020-06-14T21:00.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
