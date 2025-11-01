package com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.mapper;

import com.cirino.rafaela.inditex.pricingservice.domain.model.Money;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;
import com.cirino.rafaela.inditex.pricingservice.infrastructure.persistence.entity.PriceEntity;


/**
 * Converts between entity and domain model.
 */
public class PriceMapper {

    private PriceMapper() {
        // Utility class, no instances allowed
    }

    public static Price toDomain(PriceEntity entity) {
        return Price.builder()
                .brandId(entity.getBrandId())
                .brandName(entity.getBrandName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .money(new Money(entity.getPrice(), entity.getCurrency()))
                .build();
    }
}
