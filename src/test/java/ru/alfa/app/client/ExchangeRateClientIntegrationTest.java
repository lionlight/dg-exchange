package ru.alfa.app.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.alfa.app.config.RatesMocks;
import ru.alfa.app.config.WireMockConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
class ExchangeRateClientIntegrationTest {

    @Autowired
    private WireMockServer mockRateServiceServer;

    @Autowired
    private ExchangeRateClient client;

    @BeforeEach
    void setUp() throws IOException {
        RatesMocks.setupMockBooksResponse(mockRateServiceServer);
    }

    @Test
    public void whenGetRates_thenBaseShouldBeReturned() {
        assertEquals("USD", client.getRates(anyString(), anyString(), "RUB").getBase());
    }
    
}
