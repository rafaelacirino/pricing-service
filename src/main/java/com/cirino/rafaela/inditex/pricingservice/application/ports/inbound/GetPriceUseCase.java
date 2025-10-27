package com.cirino.rafaela.inditex.pricingservice.application.ports.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;

import java.time.LocalDateTime;

public interface GetPriceUseCase {

    /**
     * Gets price.
     *
     * @param applicationDate the application date
     * @param productId       the product id
     * @param brandId         the brand id
     * @return the price
     */
    PriceResponseDto getPrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
