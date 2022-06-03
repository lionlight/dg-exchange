package ru.alfa.app.services;

import com.google.gson.Gson;
import feign.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.model.Rate;
import ru.alfa.app.services.enums.OpenExchangeRatesTickers;

import java.io.IOException;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static ru.alfa.app.services.enums.OpenExchangeRatesJsonProperties.RATES;

@Service
@Getter
@Slf4j
public class ExchangeRatesService extends AbstractService {

    @Value("${app.rate.api-url}")
    private String serviceUrl;

    @Value("${app.rate.app-id}")
    private String appId;

    @Value("${app.rate.general-base}")
    private String base;

    public boolean isNowRateGreatest(Rate now, Rate yesterday) {
        if (now != null && yesterday != null)
            return now.getValue() > yesterday.getValue();
        else return false;
    }

    public Rate getRate(Response response, OpenExchangeRatesTickers rateType) throws IOException {
        Map<String, Object> parseResponseBodyJsonMap =
                parseResponseBody(IOUtils.toString(response.body().asInputStream()));

        return getRate(parseResponseBodyJsonMap, rateType);
    }

    public Rate getRate(Map<String, Object> map, OpenExchangeRatesTickers rateType) {
        Gson gson = new Gson();

        String rateValue = gson.toJsonTree(map)
                .getAsJsonObject()
                .get(RATES.getPropertyName())
                .getAsJsonObject()
                .get(rateType.getRateTypeName()).getAsString();

        Rate rate = new Rate();
        rate.setTicker(rateType.getRateTypeName());
        rate.setValue(parseDouble(rateValue));

        return rate;
    }

    public String getAppId() {
        return appId;
    }

    public String getGeneralBase() {
        return base;
    }
}
