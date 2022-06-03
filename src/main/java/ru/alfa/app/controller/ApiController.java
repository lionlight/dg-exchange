package ru.alfa.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
import ru.alfa.app.utils.DateUtils;

import java.io.IOException;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor
@Api(value = "ApiController", description = "Output the result/Выдать результат", tags = {"ApiController"})
public class ApiController {

    public static final String API_V_1_REDIRECT_URL = "/api/v1/gifs?tag=";
    private GiphyClient giphyClient;
    private ExchangeRateClient exchangeRateClient;
    private ExchangeRatesService exchangeRatesService;
    private GiphyService giphyService;

    @ApiOperation(value = "rate from openexhcangerates api", notes = "")
    @RequestMapping(method = RequestMethod.GET,
            value = "/rates",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView getRates(String base) {
        final String APP_ID = exchangeRatesService.getAppId();
        final String GENERAL_BASE = exchangeRatesService.getGeneralBase();

        final DateUtils dateUtils = new DateUtils();

        var now = exchangeRateClient.getRates(dateUtils.getNowDateAsString() + ".json", APP_ID, GENERAL_BASE);

        var yesterday = exchangeRateClient.getRates(dateUtils.getYesterdayAsString() + ".json", APP_ID, GENERAL_BASE);

        if (exchangeRatesService.isNowRateGreatest(now, yesterday, base)) {
            return new RedirectView(API_V_1_REDIRECT_URL + giphyService.getPositiveTag());
        } else return new RedirectView(API_V_1_REDIRECT_URL + giphyService.getNegativeTag());
    }

    @ApiOperation(value = "gif from giphy api", notes = "")
    @RequestMapping(method = RequestMethod.GET,
            value = "/gifs",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView getGifs(@RequestParam String tag) throws IOException {
        var response = giphyClient.getGif(giphyService.getApiKey(), tag);
        return new RedirectView(giphyService.getGifUrl(response));
    }

}
