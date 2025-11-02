package com.cirino.rafaela.inditex.pricingservice.application.service;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.mapper.PriceResponseMapper;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service responsible for retrieving the applicable price for a product and brand
 * at a specific date and time. Implements the {@link GetPriceUseCase} interface.
 * This service uses caching to optimize repeated queries and delegates data access to the
 * {@link PriceRepositoryPort}, applying business rules such as selecting the price with the highest priority.
 * */
@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepository;

    public PriceService(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Cacheable(value = "prices", key = "#applicationDate.toString() + '-' + #productId + '-' + #brandId")
    @Override
    public PriceResponseDto getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        Price price = priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(applicationDate, productId, brandId));

        return PriceResponseMapper.toDto(price);
    }
}
