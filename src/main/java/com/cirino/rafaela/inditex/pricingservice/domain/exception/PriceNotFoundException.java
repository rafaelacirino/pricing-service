package com.cirino.rafaela.inditex.pricingservice.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(LocalDateTime applicationDate, Long productId, Long brandId) {
        super(String.format("Price not found for product %d, brand %d at %s.", productId, brandId, applicationDate));
    }
}
