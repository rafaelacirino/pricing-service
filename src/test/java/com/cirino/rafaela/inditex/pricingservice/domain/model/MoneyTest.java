package com.cirino.rafaela.inditex.pricingservice.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidValues() {
        BigDecimal amount = new BigDecimal("25.45");
        String currency = "EUR";

        Money money = new Money(amount, currency);

        assertEquals(amount, money.getAmount());
        assertEquals(currency, money.getCurrency());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Money(null, "EUR")
        );

        assertEquals("Amount and currency required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCurrencyIsNull() {
        Executable action = () -> new Money(new BigDecimal("10.00"), null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Amount and currency required", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCurrencyIsBlank() {
        Executable action = () -> new Money(new BigDecimal("10.00"), " ");
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    void shouldThrowExceptionWithCorrectMessageWhenCurrencyIsBlank() {
        Executable action = () -> new Money(new BigDecimal("10.00"), " ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("Amount and currency required", exception.getMessage());
    }

    @Test
    void shouldCompareMoneyObjectsCorrectly() {
        Money m1 = new Money(new BigDecimal("10.00"), "USD");
        Money m2 = new Money(new BigDecimal("10.00"), "USD");
        Money m3 = new Money(new BigDecimal("15.00"), "USD");

        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void shouldGenerateReadableToString() {
        Money money = new Money(new BigDecimal("99.99"), "EUR");
        String expected = "Money(amount=99.99, currency=EUR)";

        assertEquals(expected, money.toString());
    }
}
