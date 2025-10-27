package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository;

import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(statements = "DELETE FROM prices", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    @DisplayName("Should return price when date is within range")
    void testFindByProductIdAndBrandIdAndDateRange() {
        // Arrange
        PriceEntity price = new PriceEntity();
        price.setBrandId(1L);
        price.setBrandName("ZARA");
        price.setProductId(35455L);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price.setPriceList(1);
        price.setPriority(0);
        price.setPrice(new BigDecimal("35.50"));
        price.setCurrency("EUR");

        priceRepository.save(price);

        // Act
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        List<PriceEntity> results = priceRepository.findApplicablePrices(applicationDate, 35455L, 1L);

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(new BigDecimal("35.50"), results.stream().findFirst().get().getPrice());
    }

    @Test
    @DisplayName("Should return empty list when date is outside range")
    void testFindByProductIdAndBrandIdAndDateRange_noMatch() {
        // Arrange
        PriceEntity price = new PriceEntity();
        price.setBrandId(1L);
        price.setBrandName("ZARA");
        price.setProductId(35455L);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price.setEndDate(LocalDateTime.of(2020, 6, 14, 23, 59));
        price.setPriceList(1);
        price.setPriority(0);
        price.setPrice(new BigDecimal("35.50"));
        price.setCurrency("EUR");

        priceRepository.save(price);

        // Act
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        List<PriceEntity> results = priceRepository.findApplicablePrices(applicationDate, 35455L, 1L);

        // Assert
        assertTrue(results.isEmpty());
    }
}
