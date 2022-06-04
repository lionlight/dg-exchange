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
import ru.alfa.app.client.GiphyClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockWebServiceServer
public class GiphyIntegrationTest {

    @Autowired
    private GiphyClient client;

    @Autowired
    private GiphyService giphyService;
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
    void getGifs_With_Parameters_Positive_Tag_Status_Ok() {
        stubFor(any(anyUrl()).willReturn(ok()));
        Request.HttpMethod expectedHTTPMethod = Request.HttpMethod.GET;
        String expectedQueryLine = "?api_key=" + giphyService.getApiKey() + "&q=" + giphyService.getPositiveTag();
        int expectedStatusCode = 200;

        Response response = client.getGif(giphyService.getApiKey(),
                giphyService.getPositiveTag());

        assertEquals(expectedHTTPMethod, response.request().httpMethod());
        assertEquals(expectedQueryLine, response.request().requestTemplate().queryLine());
        assertEquals(expectedStatusCode, response.status());
    }

    @Test
    void getGifs_With_Parameters_Negative_Tag_Status_Ok() {
        Request.HttpMethod expectedHTTPMethod = Request.HttpMethod.GET;
        String expectedQueryLine = "?api_key=" + giphyService.getApiKey() + "&q=" + giphyService.getNegativeTag();
        int expectedStatusCode = 200;

        Response response = client.getGif(giphyService.getApiKey(),
                giphyService.getNegativeTag());

        assertEquals(expectedHTTPMethod, response.request().httpMethod());
        assertEquals(expectedQueryLine, response.request().requestTemplate().queryLine());
        assertEquals(expectedStatusCode, response.status());
    }

    @AfterEach
    void stop() {
        wireMockServer.stop();
    }
}
