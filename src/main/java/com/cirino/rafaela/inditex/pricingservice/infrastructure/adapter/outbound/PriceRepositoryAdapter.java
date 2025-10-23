package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.outbound;

import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.entity.Price;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

/**
 * The type Price repository adapter.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceRepository  priceRepository;

    public PriceRepositoryAdapter(PriceRepository priceRepository, PriceRepository priceRepository1) {
        this.priceRepository = priceRepository1;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository
                .findByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfter(productId, brandId,
                                                                            applicationDate, applicationDate)
                .stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority))
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity priceEntity) {
        return new Price(
                priceEntity.getBrandId(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getPriceList(),
                priceEntity.getProductId(),
                priceEntity.getPriority(),
                new Money(priceEntity.getPrice(), priceEntity.getCurrency())
        );
    }
}
