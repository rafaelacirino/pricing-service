package com.cirino.rafaela.inditex.pricingservice.application.ports.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;

import java.time.LocalDateTime;

public interface GetPriceUseCase {

    /**
     * Interface for price inquiry use cases.
     * Defines the contract to retrieve the applicable price.
     *
     * @param applicationDate the application date
     * @param productId       the product id
     * @param brandId         the brand id
     * @return DTO with applicable price
     */
    PriceResponseDto getPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}