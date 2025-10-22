package com.cirino.rafaela.inditex.pricingservice.domain.entity;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Price {

    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Integer priceList;
    private final Long productId;
    private final Integer priority;
    private final Money money;

    public Price(Long brandId, LocalDateTime startDate,
                 LocalDateTime endDate, Integer priceList, Long productId,
                 Integer priority, Money money) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.money = money;
    }
}
