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
import ru.alfa.app.model.Rate;
import ru.alfa.app.services.ExchangeRatesService;
import ru.alfa.app.services.GiphyService;

import java.io.IOException;
import java.util.Locale;

import static ru.alfa.app.services.enums.OpenExchangeRatesTickers.valueOf;
import static ru.alfa.app.utils.DateUtils.getCurrentDateAsFormattedString;
import static ru.alfa.app.utils.DateUtils.getYesterdaysDateAsFormattedString;

@RestController
@RequestMapping("/v1/")
@Slf4j
@AllArgsConstructor
@Api(value = "ApiController", description = "Output the result/Выдать результат", tags = {"ApiController"})
public class ApiController {

    public static final String API_V_1_REDIRECT_URL = "/api/v1/gifs";
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

        final String APP_ID = exchangeRatesService.getAppId();
        final String GENERAL_BASE = exchangeRatesService.getGeneralBase();

        final String nowDate = getCurrentDateAsFormattedString();
        final String yesterdayDate = getYesterdaysDateAsFormattedString();

        var now = exchangeRateClient.getRates(nowDate + ".json", APP_ID, GENERAL_BASE);

        String clientNameRequestWithNowDate = now.request().requestTemplate().feignTarget().name();

        log.info(clientNameRequestWithNowDate
                + " send request to "
                + exchangeRatesService.getServiceUrl()
                + " with now date: ");

        var yesterday = exchangeRateClient.getRates(yesterdayDate + ".json", APP_ID, GENERAL_BASE);

        String clientNameRequestWithYesterdayDate = yesterday.request().requestTemplate().feignTarget().name();

        log.info(clientNameRequestWithYesterdayDate
                + " send request to "
                + exchangeRatesService.getServiceUrl()
                + " with yesterday date (for compare rates): ");

        final Rate nowRate = exchangeRatesService.getRate(now, valueOf(base.toUpperCase(Locale.ROOT)));
        final Rate yesterdayRate = exchangeRatesService.getRate(yesterday, valueOf(base.toUpperCase(Locale.ROOT)));

        String redirectQuery = "?tag=";

        if (exchangeRatesService.isNowRateGreatest(nowRate, yesterdayRate)) {
            log.info("Redirect to " + API_V_1_REDIRECT_URL);

            return new RedirectView(API_V_1_REDIRECT_URL + redirectQuery + giphyService.getPositiveTag());
        } else {
            log.info("Redirect to " + API_V_1_REDIRECT_URL);

            return new RedirectView(API_V_1_REDIRECT_URL + redirectQuery + giphyService.getNegativeTag());
        }
    }

    @ApiOperation(value = "gif from giphy api", notes = "")
    @RequestMapping(method = RequestMethod.GET,
            value = "/gifs",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public RedirectView getGifs(@RequestParam String tag) throws IOException {
        var response = giphyClient.getGif(giphyService.getApiKey(), tag);
        log.info("Client send request to " + giphyService.getServiceUrl());
        return new RedirectView(giphyService.getGifUrl(response));
    }

}
