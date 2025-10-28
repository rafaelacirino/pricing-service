package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.outbound;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceRepositoryAdapter adapter;

    @Test
    void shouldReturnHighestPriorityPriceConvertedToDomain() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        PriceEntity entity = new PriceEntity();
        entity.setProductId(productId);
        entity.setBrandId(brandId);
        entity.setBrandName("ZARA");
        entity.setStartDate(date.minusHours(1));
        entity.setEndDate(date.plusHours(2));
        entity.setPriceList(2);
        entity.setPriority(1);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        when(priceRepository.findApplicablePrices(date, productId, brandId))
                .thenReturn(Optional.of(entity));

        Optional<Price> result = adapter.findApplicablePrice(date, productId, brandId);

        assertTrue(result.isPresent());
        Price price = result.get();
        assertEquals(productId, price.getProductId());
        assertEquals(brandId, price.getBrandId());
        assertEquals("ZARA", price.getBrandName());
        assertEquals(2, price.getPriceList());
        assertEquals(1, price.getPriority());
        assertEquals(new BigDecimal("35.50"), price.getMoney().getAmount());
        assertEquals("EUR", price.getMoney().getCurrency());
    }

    @Test
    void shouldReturnEmptyWhenNoPricesFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findApplicablePrices(date, productId, brandId)).thenReturn(Optional.empty());

        Optional<Price> result = adapter.findApplicablePrice(date, productId, brandId);

        assertTrue(result.isEmpty());
    }
}
