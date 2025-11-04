package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
@Validated
@SuppressWarnings("unused")
class TestExceptionController {


    @GetMapping("/price-not-found")
    public void throwPriceNotFound() {
        throw new PriceNotFoundException(LocalDateTime.of(2020, 6, 14, 10, 0), 35455L, 1L);
    }

    @GetMapping("/generic-error")
    public void throwGenericError() {
        throw new RuntimeException("Database down");
    }

    @GetMapping("/validate/{id}")
    public void validatePositiveId(@PathVariable @Positive(message = "must be greater than 0") int id) {
        // empty method
    }

    @GetMapping("/missing-param")
    public void missingRequestParam(@RequestParam String name) {
        // empty method
    }

    @PostMapping("/body-validation")
    public void validateBody(@Valid @RequestBody DummyRequest body) {
        // empty method
    }

    public static class DummyRequest {

        @NotBlank(message = "must not be blank")
        private String username;
    }
}
