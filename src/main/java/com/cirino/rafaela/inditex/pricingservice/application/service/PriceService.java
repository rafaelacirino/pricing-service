package com.cirino.rafaela.inditex.pricingservice.application.service;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import com.cirino.rafaela.inditex.pricingservice.application.ports.outbound.PriceRepositoryPort;
import com.cirino.rafaela.inditex.pricingservice.domain.entity.Price;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepository;

    public PriceService(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public PriceResponseDto getPrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        Price price = priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(applicationDate, productId, brandId));

        return new PriceResponseDto(
                price.getProductId(),
                price.getBrandId(),
                price.getBrandName(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getMoney().getAmount(),
                price.getMoney().getCurrency()
        );
    }
}
