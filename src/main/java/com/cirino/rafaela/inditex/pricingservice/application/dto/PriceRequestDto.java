package com.cirino.rafaela.inditex.pricingservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO representing the input parameters for querying the applicable price.
 * Used as input for the pricing endpoint.
 */
@Setter
@Getter
public class PriceRequestDto {

    @NotNull
    @Schema(example = "2020-06-14T10:00:00", description = "Date and time of application")
    private LocalDateTime applicationDate;

    @NotNull
    @Schema(example = "35455", description = "Product identifier")
    private Long productId;

    @NotNull
    @Schema(example = "1", description = "Brand identifier (e.g., 1 for ZARA)")
    private Long brandId;
}
