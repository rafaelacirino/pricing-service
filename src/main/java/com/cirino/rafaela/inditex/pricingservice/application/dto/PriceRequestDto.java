package com.cirino.rafaela.inditex.pricingservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PriceRequestDto {

    @NotNull
    @Schema(example = "2020-06-14T10:00:00", description = "Date and time of application")
    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
