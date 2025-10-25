package com.cirino.rafaela.inditex.pricingservice.application.service;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.entity.Price;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepository;

    @InjectMocks
    private PriceService priceService;

    @Test
    void shouldReturnPriceResponseDtoWhenPriceIsFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price mockPrice = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .brandName("ZARA")
                .priceList(1)
                .startDate(date.minusHours(1))
                .endDate(date.plusHours(2))
                .money(new Money(new BigDecimal("35.50"), "EUR"))
                .priority(0)
                .build();

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(mockPrice));

        PriceResponseDto response = priceService.getPrice(date, productId, brandId);

        assertEquals(productId, response.getProductId());
        assertEquals(brandId, response.getBrandId());
        assertEquals("ZARA", response.getBrandName());
        assertEquals(1, response.getPriceList());
        assertEquals(mockPrice.getStartDate(), response.getStartDate());
        assertEquals(mockPrice.getEndDate(), response.getEndDate());
        assertEquals(new BigDecimal("35.50"), response.getPrice());
        assertEquals("EUR", response.getCurrency());
    }

    @Test
    void shouldThrowPriceNotFoundExceptionWhenPriceIsNotFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.empty());

        PriceNotFoundException exception = assertThrows(
                PriceNotFoundException.class,
                () -> priceService.getPrice(date, productId, brandId)
        );

        assertEquals("Price not found for product 35455, brand 1 at 2020-06-14T21:00.", exception.getMessage());
    }
}
