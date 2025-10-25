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

    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository
                .findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, brandId,
                                                                            applicationDate, applicationDate)
                .stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority))
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity priceEntity) {
        return Price.builder()
                .brandId(priceEntity.getBrandId())
                .brandName(priceEntity.getBrandName())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .priceList(priceEntity.getPriceList())
                .productId(priceEntity.getProductId())
                .priority(priceEntity.getPriority())
                .money(new Money(priceEntity.getPrice(), priceEntity.getCurrency()))
                .build();
    }
}
