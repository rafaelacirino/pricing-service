package com.cirino.rafaela.inditex.pricingservice.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Domain model. Immutable via Builder.
 */
@Getter
@Builder
public class Price {

    private final Long brandId;
    private final String brandName;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final Money money;
}
