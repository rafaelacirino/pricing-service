package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.ErrorResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    private GetPriceUseCase getPriceUseCase;
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        getPriceUseCase = mock(GetPriceUseCase.class);
        priceController = new PriceController(getPriceUseCase);
    }

    @Test
    void shouldReturnPriceResponseDtoWhenPriceIsFound() {
        // Arrange
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        PriceResponseDto dto = PriceResponseDto.builder()
                        .productId(35455L)
                        .brandId(1L)
                        .brandName("ZARA")
                        .price(new BigDecimal("35.50"))
                        .priceList(1)
                        .startDate(date)
                        .endDate(date.plusDays(1))
                        .currency("EUR")
                        .build();
        when(getPriceUseCase.getPrice(date, 35455L, 1L)).thenReturn(dto);

        // Act
        ResponseEntity<Object> response = priceController.getPrice(date, 35455L, 1L);

        // Assert
        assertInstanceOf(PriceResponseDto.class, response.getBody());
        PriceResponseDto body = (PriceResponseDto) response.getBody();
        assertEquals(new BigDecimal("35.50"), body.getPrice());
    }

    @Test
    void shouldReturn404WhenPriceNotFound() {
        // Arrange
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        when(getPriceUseCase.getPrice(date, 35455L, 1L))
                .thenThrow(new PriceNotFoundException(date, 35455L, 1L));

        // Act
        ResponseEntity<Object> response = priceController.getPrice(date, 35455L, 1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ErrorResponseDto.class, response.getBody());
        ErrorResponseDto error = (ErrorResponseDto) response.getBody();
        assertEquals("PriceNotFoundException", error.getError());
        assertTrue(error.getMessage().contains("Price not found for product 35455, brand 1 at 2020-06-15T10:00"));
    }

    @Test
    void shouldReturn500WhenUnexpectedExceptionOccurs() {
        // Arrange
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        when(getPriceUseCase.getPrice(date, 35455L, 1L))
                .thenThrow(new RuntimeException("Database down"));

        // Act
        ResponseEntity<Object> response = priceController.getPrice(date, 35455L, 1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertInstanceOf(ErrorResponseDto.class, response.getBody());
        ErrorResponseDto error = (ErrorResponseDto) response.getBody();
        assertEquals("InternalServerError", error.getError());
        assertEquals("Unexpected error occurred", error.getMessage());
    }
}
