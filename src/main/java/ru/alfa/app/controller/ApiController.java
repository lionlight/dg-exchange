package ru.alfa.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.alfa.app.client.ExchangeRateClient;
import ru.alfa.app.client.GiphyClient;
import ru.alfa.app.services.ExchangeRatesService;
import ru.alfa.app.services.GiphyService;

import java.io.IOException;

@RestController
@RequestMapping("/v1/")
@Slf4j
@AllArgsConstructor
@Api(value = "ApiController", description = "Output the result/Выдать результат", tags = {"ApiController"})
public class ApiController {

    public static final String API_V_1_REDIRECT_TO_GIPHY = "/api/v1/gifs?tag=";
    private GiphyClient giphyClient;
    private ExchangeRateClient exchangeRateClient;
    private ExchangeRatesService exchangeRatesService;
    private GiphyService giphyService;

    @ApiOperation(value = "rate from openexhcangerates api", notes = "")
    @RequestMapping(method = RequestMethod.GET,
            value = "/rates",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView getRates(String base) throws IOException {
        exchangeRatesService.sendRequestFromClient(exchangeRateClient);

        boolean isRich = exchangeRatesService.compareTwoRates(base);

        log.info("Redirect to " + API_V_1_REDIRECT_TO_GIPHY);

        if (isRich) {
            return new RedirectView(API_V_1_REDIRECT_TO_GIPHY + giphyService.getPositiveTag());
        } else {
            return new RedirectView(API_V_1_REDIRECT_TO_GIPHY + giphyService.getNegativeTag());
        }
    }

    @ApiOperation(value = "gif from giphy api", notes = "")
    @RequestMapping(method = RequestMethod.GET,
            value = "/gifs",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView getGifs(@RequestParam String tag) throws IOException {
        String apiKey = giphyService.getApiKey();
        int offset = giphyService.generateOffset();

        var response = giphyClient.getGif(apiKey, tag, offset);

        String redirectUrl = giphyService.getGifUrl(response);

        String serviceUrl = giphyService.getServiceUrl();
        log.info("Client send request to " + serviceUrl);

        return new RedirectView(redirectUrl);
    }

}
