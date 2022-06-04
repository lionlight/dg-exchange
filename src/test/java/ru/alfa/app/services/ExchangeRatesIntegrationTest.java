package ru.alfa.app.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alfa.app.client.ExchangeRateClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.alfa.app.utils.DateUtils.getCurrentDateAsFormattedString;
import static ru.alfa.app.utils.DateUtils.getYesterdaysDateAsFormattedString;

@SpringBootTest
@AutoConfigureMockWebServiceServer
public class ExchangeRatesIntegrationTest {
    @Autowired
    private ExchangeRateClient client;
    @Autowired
    private ExchangeRatesService exchangeRatesService;
    private WireMockServer wireMockServer;

    @Value("${service.integration-test.wiremock-host}")
    private String wireMockHost;

    @Value("${service.integration-test.wiremock-port}")
    private int wireMockPort;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer();
        configureFor(wireMockHost, wireMockPort);
        wireMockServer.start();
    }

    @Test
    void get_Rates_With_Current_Date_Status_Ok() {
        Request.HttpMethod expectedHTTPMethod = Request.HttpMethod.GET;
        String expectedQueryLine = "?date=" + getCurrentDateAsFormattedString()
                + ".json&"
                + "app_id=" + exchangeRatesService.getAppId()
                + "&base=" + exchangeRatesService.getGeneralBase();
        int expectedStatusCode = 200;

        Response response = client.getRates(getCurrentDateAsFormattedString() + ".json",
                exchangeRatesService.getAppId(),
                exchangeRatesService.getGeneralBase());

        assertEquals(expectedHTTPMethod, response.request().httpMethod());
        assertEquals(expectedQueryLine, response.request().requestTemplate().queryLine());
        assertEquals(expectedStatusCode, response.status());
    }

    @Test
    void get_Rates_Since_Yesterdays_Date_Status_Ok() {
        stubFor(any(anyUrl()).willReturn(ok()));

        Request.HttpMethod expectedHTTPMethod = Request.HttpMethod.GET;
        String expectedQueryLine = "?date=" + getYesterdaysDateAsFormattedString()
                + ".json&"
                + "app_id=" + exchangeRatesService.getAppId()
                + "&base=" + exchangeRatesService.getGeneralBase();
        int expectedStatusCode = 200;

        Response response = client.getRates(getYesterdaysDateAsFormattedString() + ".json",
                exchangeRatesService.getAppId(),
                exchangeRatesService.getGeneralBase());

        assertEquals(expectedHTTPMethod, response.request().httpMethod());
        assertEquals(expectedQueryLine, response.request().requestTemplate().queryLine());
        assertEquals(expectedStatusCode, response.status());
    }

    @AfterEach
    void stop() {
        wireMockServer.stop();
    }
}
