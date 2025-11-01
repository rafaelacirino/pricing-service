package com.cirino.rafaela.inditex.pricingservice.application.mapper;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.domain.model.Price;

/**
 * Converts domain model Price to PriceResponseDto for API response.
 */
public class PriceResponseMapper {

    private PriceResponseMapper() {
        // Utility class, no instances allowed
    }

    public static PriceResponseDto toDto(Price price) {
        return PriceResponseDto.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .brandName(price.getBrandName())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getMoney().getAmount())
                .currency(price.getMoney().getCurrency())
                .build();
    }
}
