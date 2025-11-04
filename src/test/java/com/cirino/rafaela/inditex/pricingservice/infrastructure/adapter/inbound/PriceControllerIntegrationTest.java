package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    private static final String BASE_URL = "/api/v1/brands/1/products/35455/price";

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Valid scenarios - should return applicable prices")
    class ValidScenarios {

        @Test
        @DisplayName("Should return price list 1 at 10:00 on 14th June for ZARA")
        void shouldReturnPriceList1_whenDateIsJune14At10h() throws Exception {
            // GIVEN / WHEN / THEN
            mockMvc.perform(get(BASE_URL)
                            .param("applicationDate", "2020-06-14T10:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.price").value(35.50));
        }

        @Test
        @DisplayName("Should return price list 2 at 16:00 on 14th June for ZARA")
        void shouldReturnPriceList2_whenDateIsJune14At16h() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("applicationDate", "2020-06-14T16:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList").value(2))
                    .andExpect(jsonPath("$.price").value(25.45));
        }

        @Test
        @DisplayName("Should return price list 4 at 21:00 on 16th June for ZARA")
        void shouldReturnPriceList4_whenDateIsJune16At21h() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("applicationDate", "2020-06-16T21:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.priceList").value(4))
                    .andExpect(jsonPath("$.price").value(38.95));
        }
    }

    @Nested
    @DisplayName("Invalid or out-of-range scenarios - should return errors")
    class ErrorScenarios {

        @Test
        @DisplayName("Should return 404 when date is outside any price period (June 14 at 21:00)")
        void shouldReturnNotFound_whenDateIsOutsideRange() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("applicationDate", "2020-06-14T21:00:00"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 404 when date is before any price period (June 13 at 10:00)")
        void shouldReturnNotFound_whenDateIsBeforeAllRanges() throws Exception {
            mockMvc.perform(get(BASE_URL)
                            .param("applicationDate", "2020-06-13T10:00:00"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when applicationDate is missing")
        void shouldReturnBadRequest_whenApplicationDateMissing() throws Exception {
            mockMvc.perform(get(BASE_URL))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when brandId is invalid (zero or negative)")
        void shouldReturnBadRequest_whenBrandIdInvalid() throws Exception {
            mockMvc.perform(get("/api/v1/brands/0/products/35455/price")
                            .param("applicationDate", "2020-06-14T10:00:00"))
                    .andExpect(status().isBadRequest());
        }
    }
}
