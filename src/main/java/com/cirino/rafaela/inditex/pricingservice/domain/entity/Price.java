package com.cirino.rafaela.inditex.pricingservice.domain.entity;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Price {

    private final Long brandId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long productId;
    private final Integer priority;
    private final Money money;

    public Price(Long brandId, LocalDateTime startDate,
                 LocalDateTime endDate, Long productId,
                 Integer priority, Money money) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.priority = priority;
        this.money = money;
    }

    public Long getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Money getMoney() {
        return money;
    }
}
