package com.cirino.rafaela.inditex.pricingservice.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for standardized error responses returned by the API.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standardized error response")
public class ErrorResponseDto {

    @Schema(description = "HTTP error code", example = "404")
    private int status;

    @Schema(description = "Error type or exception name", example = "PriceNotFoundException")
    private String error;

    @Schema(description = "Detailed error message", example = "Price not found for product 35455, brand 1 at 2020-06-14T10:00:00.")
    private String message;

    @Schema(description = "Timestamp of the error occurrence", example = "2020-06-14T10:00:00")
    private LocalDateTime timestamp;
}
