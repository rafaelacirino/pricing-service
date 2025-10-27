package com.cirino.rafaela.inditex.pricingservice.domain.entity;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
