package com.cirino.rafaela.inditex.pricingservice.domain.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldReturnNotFoundResponseWhenPriceNotFoundExceptionIsHandled() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        PriceNotFoundException exception = new PriceNotFoundException(date, productId, brandId);

        ResponseEntity<String> response = handler.handlePriceNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Price not found for product 35455, brand 1 at 2020-06-14T21:00.", response.getBody());
    }
}
