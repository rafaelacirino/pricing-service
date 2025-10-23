package com.cirino.rafaela.inditex.pricingservice.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pricing Service API",
                version = "1.0.0",
                description = "REST Service for querying applicable prices"
        )
)
public class OpenApiConfig {
    //Empty class
}
