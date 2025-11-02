package com.cirino.rafaela.inditex.pricingservice.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Value object representing a monetary amount and its currency.
 * Used to encapsulate pricing values in the domain model.
 */
@Getter
@EqualsAndHashCode
@ToString
@Builder
@Schema
public class Money {

    @Schema(example = "25.45", description = "Monetary amount")
    private final BigDecimal amount;

    @Schema(example = "EUR", description = "Currency code in ISO format")
    private final String currency;

    /**
     * Constructs a Money object with validation.
     *
     * @param amount   the monetary amount
     * @param currency the currency code (must not be null or blank)
     * @throws IllegalArgumentException if amount or currency is null or blank
     */
    public Money(BigDecimal amount, String currency) {
        if (amount == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Amount and currency required");
        }
        this.amount = amount;
        this.currency = currency;
    }
}
