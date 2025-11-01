package com.cirino.rafaela.inditex.pricingservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO representing the response returned by the pricing API.
 * Contains the applicable price details for a given product, brand, and date.
 */
@Getter
@Builder
@Schema(description = "Response with applicable price and HATEOAS links")
public class PriceResponseDto {

    @Schema(example = "12345", description = "Product ID")
    private Long productId;

    @Schema(example = "1", description = "Brand ID")
    private Long brandId;

    @Schema(example = "ZARA", description = "Brand name")
    private String  brandName;

    @Schema(example = "25.45", description = "Price amount")
    private BigDecimal price;

    @Schema(example = "2", description = "Price list ID")
    private Integer priceList;

    @Schema(example = "2020-06-14T15:00:00", description = "Start date of price validity")
    private LocalDateTime startDate;

    @Schema(example = "2020-06-14T18:30:00", description = "End date of price validity")
    private LocalDateTime endDate;

    @Schema(example = "EUR", description = "Currency code")
    private String currency;
}
