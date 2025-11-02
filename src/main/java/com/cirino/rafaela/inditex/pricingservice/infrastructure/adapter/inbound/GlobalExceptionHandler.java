package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.ErrorResponseDto;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for the Pricing Service API.
 * Captures and transforms exceptions into standardized {@link ErrorResponseDto} objects,
 * ensuring consistent error responses across the application.
 * This class is annotated with {@link RestControllerAdvice}, allowing it to intercept exceptions
 * thrown by any controller. It is hidden from Swagger documentation via {@link Hidden}.
 */
@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    /**
     * Handles {@link PriceNotFoundException} when no applicable price is found.
     *
     * @param ex the exception thrown when a price is not found
     * @return a {@link ResponseEntity} containing a 404 error response with details
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        "PriceNotFoundException",
                        ex.getMessage(),
                        LocalDateTime.now()
                )
        );
    }

    /**
     * Handles any unexpected exceptions not explicitly mapped.
     *
     * @return a {@link ResponseEntity} containing a 500 error response with a generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDto(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "InternalServerError",
                        "Unexpected error occurred",
                        LocalDateTime.now()
                )
        );
    }
}
