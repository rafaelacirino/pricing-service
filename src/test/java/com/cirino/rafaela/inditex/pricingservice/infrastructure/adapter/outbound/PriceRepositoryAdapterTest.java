package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.outbound;

import com.cirino.rafaela.inditex.pricingservice.domain.entity.Price;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

        PriceEntity lowPriority = new PriceEntity();
        lowPriority.setProductId(productId);
        lowPriority.setBrandId(brandId);
        lowPriority.setBrandName("ZARA");
        lowPriority.setStartDate(date.minusHours(1));
        lowPriority.setEndDate(date.plusHours(2));
        lowPriority.setPriceList(1);
        lowPriority.setPriority(0);
        lowPriority.setPrice(new BigDecimal("25.45"));
        lowPriority.setCurrency("EUR");

        PriceEntity highPriority = new PriceEntity();
        highPriority.setProductId(productId);
        highPriority.setBrandId(brandId);
        highPriority.setBrandName("ZARA");
        highPriority.setStartDate(date.minusHours(1));
        highPriority.setEndDate(date.plusHours(2));
        highPriority.setPriceList(2);
        highPriority.setPriority(1);
        highPriority.setPrice(new BigDecimal("35.50"));
        highPriority.setCurrency("EUR");

        when(priceRepository.findApplicablePrices(date, productId, brandId))
                .thenReturn(List.of(lowPriority, highPriority));

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

        when(priceRepository.findApplicablePrices(date, productId, brandId)).thenReturn(List.of());

        Optional<Price> result = adapter.findApplicablePrice(date, productId, brandId);

        assertTrue(result.isEmpty());
    }
}
