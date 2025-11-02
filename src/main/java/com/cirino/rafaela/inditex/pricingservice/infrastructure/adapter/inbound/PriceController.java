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
@Tag(name = "Prices", description = "Operations related to product pricing")
@RestController
@Validated
@RequestMapping("/api/v1/prices/")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping("/brands/{brandId}/products/{productId}")
    @Operation(
            summary = "Retrieve applicable price",
            description = "Returns the applicable price for a given product, brand, and application date.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful retrieval of price",
                            content = @Content(schema = @Schema(implementation = PriceResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No applicable price found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDto.class),
                                    examples = @ExampleObject(
                                            value = """
                                            {
                                              "status": 500,
                                              "error": "InternalServerError",
                                              "message": "Unexpected error occurred",
                                              "timestamp": "2025-11-02T12:57:36"
                                            }
                                            """)))
            })
    public ResponseEntity<PriceResponseDto> getPrice(
            @Parameter(description = "Brand ID (e.g., 1 for ZARA)", example = "1", required = true)
            @PathVariable Long brandId,
            @Parameter(description = "Product ID (e.g., 35455)", example = "35455", required = true)
            @PathVariable Long productId,
            @Parameter(description = "Application date and time (ISO 8601)", example = "2020-06-14T10:00:00", required = true)
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {

        PriceResponseDto response = getPriceUseCase.getPrice(applicationDate, productId, brandId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(response);
    }
}