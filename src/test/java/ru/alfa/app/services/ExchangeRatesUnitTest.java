package ru.alfa.app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alfa.app.model.Rate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ExchangeRatesUnitTest {

    @Autowired
    ExchangeRatesService exchangeRatesService;

    @Value("${app.rate.general-base}")
    private String base;

    @Test
    void isNowRateGreatest_Null_False() {
        assertFalse(exchangeRatesService.isNowRateGreatest(null, null));
    }

    @Test
    void isNowRateGreatest_True() {
        Rate now = new Rate();
        now.setTicker("RUB");
        now.setValue(62);

        Rate yesterday = new Rate();
        yesterday.setTicker("RUB");
        yesterday.setValue(61);

        assertTrue(exchangeRatesService.isNowRateGreatest(now, yesterday));
    }

    @Test
    void isNowRateGreatest_False() {
        Rate now = new Rate();
        now.setTicker("RUB");
        now.setValue(30);

        Rate yesterday = new Rate();
        yesterday.setTicker("RUB");
        yesterday.setValue(61);

        assertFalse(exchangeRatesService.isNowRateGreatest(now, yesterday));
    }

//    @Test
//    void getBase() {
//        assertEquals("USD",exchangeRatesService.getBase());
//    }
}