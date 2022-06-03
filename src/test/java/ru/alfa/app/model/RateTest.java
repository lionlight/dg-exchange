package ru.alfa.app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateTest {

    private static Rate rate;

    @BeforeEach
    void configure() {
        rate = new Rate("RUB", 61.0);
    }

    @Test
    void getTicker() {
        String expected = "RUB";
        assertEquals(expected, rate.getTicker());
    }

    @Test
    void getValue() {
        double expected = 61.0;
        assertEquals(expected, rate.getValue());
    }

    @Test
    void setTicker() {
        String expected = "USD";

        rate.setTicker("USD");

        assertEquals(expected, rate.getTicker());
    }

    @Test
    void setValue() {
        double expected = 10;

        rate.setValue(10);

        assertEquals(expected, rate.getValue());
    }
}