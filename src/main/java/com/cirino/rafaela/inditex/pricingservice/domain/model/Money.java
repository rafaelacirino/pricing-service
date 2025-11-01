package com.cirino.rafaela.inditex.pricingservice.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@Schema
public class Money {

    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        if (amount == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Amount and currency required");
        }
        this.amount = amount;
        this.currency = currency;
    }
}
