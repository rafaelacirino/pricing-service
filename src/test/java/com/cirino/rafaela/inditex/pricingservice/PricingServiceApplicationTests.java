package com.cirino.rafaela.inditex.pricingservice;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class PricingServiceApplicationTests {

    @Test
    void applicationContextLoads() {
        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
            PricingServiceApplication.main(new String[]{});
            mockedStatic.verify(() -> SpringApplication.run(PricingServiceApplication.class, new String[]{}));
        }
    }
}
