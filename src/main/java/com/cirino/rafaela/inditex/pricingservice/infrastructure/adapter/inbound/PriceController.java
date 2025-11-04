package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.ErrorResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * REST controller for querying applicable prices.
 * Input: application date, product ID, and supply chain ID.
 * Output: the highest priority price within the date range.
 */
@RestController
@Validated
@RequestMapping("/api/v1")
@Tag(name = "Prices", description = "Operations related to product pricing")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    /**
     * Retrieves the applicable price for the given brand, product, and application date.
     * Filter criteria: {@code startDate <= applicationDate <= endDate}.
     * If multiple prices match, the one with highest {@code priority} is returned.
     */
    @GetMapping("/brands/{brandId}/products/{productId}/price")
    @Operation(
            summary = "Retrieve applicable price",
            description = "Returns the applicable price for a given product, brand, and application date. "
                          + "The price applies if startDate <= applicationDate <= endDate (inclusive). "
                          + "If multiple prices match, the one with the highest priority is returned.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Price found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PriceResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "SuccessfulResponse",
                                            value = """
                                {
                                  "productId": 35455,
                                  "brandId": 1,
                                  "brandName": "ZARA",
                                  "price": 35.50,
                                  "priceList": 1,
                                  "startDate": "2020-06-14T00:00:00",
                                  "endDate": "2020-06-14T14:59:59",
                                  "currency": "EUR"
                                }
                                """))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters (validation error)",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "BadRequestExample",
                                            value = """
                                {
                                  "status": 400,
                                  "error": "BadRequest",
                                  "message": "applicationDate: must not be null",
                                  "timestamp": "2025-11-04T12:00:00"
                                }
                                """))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No applicable price found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "NotFoundExample",
                                            value = """
                                {
                                  "status": 404,
                                  "error": "PriceNotFoundException",
                                  "message": "Price not found for product 35455, brand 1 at 2020-06-14T21:00:00",
                                  "timestamp": "2025-11-04T12:00:00"
                                }
                                """))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            name = "InternalServerErrorExample",
                                            value = """
                                {
                                  "status": 500,
                                  "error": "InternalServerError",
                                  "message": "Unexpected error occurred",
                                  "timestamp": "2025-11-04T12:00:00"
                                }
                                """)))
            })
    public ResponseEntity<PriceResponseDto> getPrice(
            @Parameter(description = "Brand ID (e.g., 1 for ZARA)", example = "1", required = true)
            @PathVariable @NotNull @Positive Long brandId,
            @Parameter(description = "Product ID (e.g., 35455)", example = "35455", required = true)
            @PathVariable @NotNull @Positive Long productId,
            @Parameter(description = "Application date and time (ISO 8601)", example = "2020-06-14T10:00:00", required = true)
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull LocalDateTime applicationDate) {

        PriceResponseDto response = getPriceUseCase.getPrice(applicationDate, productId, brandId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(response);
    }
}
