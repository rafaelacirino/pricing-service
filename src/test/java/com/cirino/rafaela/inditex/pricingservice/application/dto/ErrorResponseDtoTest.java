package com.cirino.rafaela.inditex.pricingservice.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ErrorResponseDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDto error = new ErrorResponseDto(404, "NotFound", "Price not found", now);

        assertEquals(404, error.getStatus());
        assertEquals("NotFound", error.getError());
        assertEquals("Price not found", error.getMessage());
        assertEquals(now, error.getTimestamp());
    }

    @Test
    void testSetters() {
        ErrorResponseDto error = new ErrorResponseDto();
        LocalDateTime now = LocalDateTime.now();

        error.setStatus(500);
        error.setError("InternalError");
        error.setMessage("Unexpected failure");
        error.setTimestamp(now);

        assertEquals(500, error.getStatus());
        assertEquals("InternalError", error.getError());
        assertEquals("Unexpected failure", error.getMessage());
        assertEquals(now, error.getTimestamp());
    }

    @Test
    void testNoArgsConstructor() {
        ErrorResponseDto error = new ErrorResponseDto();
        assertNotNull(error);
    }
}
