package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.outbound;

import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.mapper.PriceMapper;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Adapter between JPA and domain.
 */
@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceRepository  priceRepository;

    public PriceRepositoryAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .map(PriceMapper::toDomain);
    }
}
