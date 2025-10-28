package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ResponseEntity<PriceResponseDto> response = priceController.getPrice(1L, 35455L, date);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new BigDecimal("35.50"), response.getBody().getPrice());
        assertEquals("ZARA", response.getBody().getBrandName());
    }
}
