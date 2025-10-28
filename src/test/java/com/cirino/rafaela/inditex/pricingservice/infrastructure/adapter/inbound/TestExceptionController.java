package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import com.cirino.rafaela.inditex.pricingservice.domain.exception.PriceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
class TestExceptionController {


    @GetMapping("/price-not-found")
    public void throwPriceNotFound() {
        throw new PriceNotFoundException(LocalDateTime.of(2020, 6, 14, 10, 0), 35455L, 1L);
    }

    @GetMapping("/generic-error")
    public void throwGenericError() {
        throw new RuntimeException("Database down");
    }
}
