package com.cirino.rafaela.inditex.pricingservice.domain.exception;

import java.time.LocalDateTime;

/**
 * Exception thrown when no applicable price is found for the given product, brand, and application date.
 * Typically used in the service layer to signal a missing pricing rule.
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructs a new PriceNotFoundException with a detailed message including product ID, brand ID, and application date.
     *
     * @param applicationDate the date and time when the price was expected to be applied
     * @param productId       the identifier of the product
     * @param brandId         the identifier of the brand
     */
    public PriceNotFoundException(LocalDateTime applicationDate, Long productId, Long brandId) {
        super(String.format("Price not found for product %d, brand %d at %s.", productId, brandId, applicationDate));
    }
}
