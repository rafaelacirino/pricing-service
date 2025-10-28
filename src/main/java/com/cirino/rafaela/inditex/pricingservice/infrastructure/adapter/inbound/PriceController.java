package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.PriceResponseDto;
import com.cirino.rafaela.inditex.pricingservice.application.ports.inbound.GetPriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * The type Price controller.
 */
@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping("/brands/{brandId}/products/{productId}")
    @Operation(summary = "Get applicable price", description = "Retrieve the applicable price for a product in a brand at a specific application date")
    @ApiResponse(responseCode = "200", description = "Price found")
    @ApiResponse(responseCode = "404", description = "Price not found")
    public ResponseEntity<PriceResponseDto> getPrice(
            @RequestParam Long brandId,
            @RequestParam Long productId,
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {

        PriceResponseDto response = getPriceUseCase.getPrice(applicationDate, productId, brandId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                .body(response);
    }
}
