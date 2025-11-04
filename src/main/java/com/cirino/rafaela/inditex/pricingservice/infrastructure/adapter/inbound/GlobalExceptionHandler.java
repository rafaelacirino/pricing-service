package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.application.dto.ErrorResponseDto;
import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler for the Pricing Service API.
 * Ensures consistent and well-structured error responses across all controllers.
 */
@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    /**
     * Handles validation errors (e.g. invalid parameters, missing required fields).
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<ErrorResponseDto> handleValidationException(Exception ex) {

        String message = switch (ex) {
            case ConstraintViolationException violationEx -> violationEx.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));

            case MethodArgumentNotValidException methodEx -> methodEx.getBindingResult().getFieldErrors().stream()
                    .map(f -> f.getField() + ": " + f.getDefaultMessage())
                    .collect(Collectors.joining("; "));

            case MissingServletRequestParameterException missingEx ->
                    "Missing required parameter: " + missingEx.getParameterName();

            default -> "Invalid request parameters.";
        };

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        "BadRequest",
                        message,
                        LocalDateTime.now()
                )
        );
    }

    /**
     * Handles {@link PriceNotFoundException} when no applicable price is found.
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
     * Handles unexpected internal errors.
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
