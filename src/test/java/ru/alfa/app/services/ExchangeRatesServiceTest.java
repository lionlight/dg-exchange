package ru.alfa.app.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExchangeRatesServiceTest {

    @MockBean
    ExchangeRatesService exchangeRatesService;

    @Test
    void isNowRateGreatest() {
        when(exchangeRatesService.isNowRateGreatest(notNull(), notNull(), "RUB")).thenReturn(Boolean.TRUE);
    }

    @Test
    void getAppId() {
//        ExchangeRatesService.POJO.setAPP_ID("app_id");

        when(exchangeRatesService.getAppId()).thenReturn("e");
    }

    @Test
    void getGeneralBase() {
    }
}