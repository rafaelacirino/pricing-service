package com.cirino.rafaela.inditex.pricingservice.infrastructure.adapter.inbound;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestExceptionController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404ForPriceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/price-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PriceNotFoundException"))
                .andExpect(jsonPath("$.message").value("Price not found for product 35455, brand 1 at 2020-06-14T10:00."));
    }

    @Test
    void shouldReturn500ForGenericException() throws Exception {
        mockMvc.perform(get("/test/generic-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("InternalServerError"))
                .andExpect(jsonPath("$.message").value("Unexpected error occurred"));
    }

    @Test
    void shouldReturn400ForMissingServletRequestParameterException() throws Exception {
        mockMvc.perform(get("/test/missing-param")) // falta param "name"
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BadRequest"))
                .andExpect(jsonPath("$.message").value("Missing required parameter: name"));
    }

    @Test
    void shouldReturn400ForMethodArgumentNotValidException() throws Exception {
        String invalidBody = "{}";
        mockMvc.perform(post("/test/body-validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BadRequest"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("username: must not be blank")));
    }
}
