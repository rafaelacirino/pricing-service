package com.cirino.rafaela.inditex.pricingservice.application.service;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * The type Price service.
 */
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

        return PriceResponseDto.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .brandName(price.getBrandName())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getMoney().getAmount())
                .currency(price.getMoney().getCurrency())
                .build();
    }
}
