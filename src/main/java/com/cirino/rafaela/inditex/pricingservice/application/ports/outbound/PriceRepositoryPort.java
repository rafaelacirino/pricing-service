package com.cirino.rafaela.inditex.pricingservice.application.ports.outbound;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    /**
     * Exit point for accessing the price repository.
     *
     * @param applicationDate the application date
     * @param productId       the product id
     * @param brandId         the brand id
     * @return Optional with the highest priority price
     */
    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}