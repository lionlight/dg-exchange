package ru.alfa.app.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.alfa.app.model.OpenEx;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExchangeRateClientTest {
    @MockBean
    ExchangeRateClient client;

    @Test
    void getRates() {
//        String date = "2022-06-02";
//        String appId = "5db496f900bd4fbe9a537911844e0b44";
//        OpenEx value = new OpenEx("Disclamer", "licance", "1000000", "BUT", generateValues());
//
//        when(client.getRates(date, appId, "RUB")).thenReturn(value);

    }

//    Map<String, Double> generateValues() {
//        Map<String, Double> map = new HashMap<>();
//        map.put("RUB", 62.518007);
//        map.put("EUR", 0.934642);
//        map.put("CNY", 6.6706);
//        return map;
//    }
}