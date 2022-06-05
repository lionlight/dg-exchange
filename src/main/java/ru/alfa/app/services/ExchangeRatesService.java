package ru.alfa.app.services;

import com.google.gson.Gson;
import feign.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.client.ExchangeRateClient;
import ru.alfa.app.model.Rate;
import ru.alfa.app.services.enums.OpenExchangeRatesTickers;
import ru.alfa.app.utils.DateUtils;

import java.io.IOException;
import java.util.*;

import static java.lang.Double.parseDouble;
import static ru.alfa.app.services.enums.OpenExchangeRatesJsonProperties.RATES;
import static ru.alfa.app.services.enums.OpenExchangeRatesTickers.valueOf;
import static ru.alfa.app.utils.DateUtils.getYesterdaysDateAsFormattedString;

@Service
@Slf4j
public class ExchangeRatesService extends AbstractService {

    @Value("${app.rate.api-url}")
    private String serviceUrl;

    @Value("${app.rate.app-id}")
    private String appId;

    @Value("${app.rate.general-base}")
    private String generalBase;
    @Getter
    private Map<String, Response> responses;
    private List<Rate> rates;

    public void sendRequestFromClient(final ExchangeRateClient client) {
        responses = new HashMap<>();
        List<String> strings = getFormattedDateList();

        final String current = "current";
        final String yesterday = "yesterday";

        for (int i = 0; i < strings.size(); i++) {
            final String key = i == 0 ? current : yesterday; //two requests (current, yesterday)
            send(client, strings.get(i), key);
        }
    }

    public boolean compareTwoRates(String base) throws IOException {
        getTwoRates(base);
        Rate current = Objects.requireNonNull(rates.get(0));
        Rate yesterday = Objects.requireNonNull(rates.get(1));
        return isNowRateGreatest(current, yesterday);
    }

    public String getAppId() {
        return appId;
    }

    public String getGeneralBase() {
        return generalBase;
    }

    boolean isNowRateGreatest(Rate current, Rate yesterday) {
        if (current != null && yesterday != null)
            return current.getValue() > yesterday.getValue();
        else return false;
    }

    private void send(final ExchangeRateClient client, final String date, final String key) {
        final String APP_ID = getAppId();
        final String GENERAL_BASE = getGeneralBase();

        var response = client.getRates(date + ".json", APP_ID, GENERAL_BASE);

        responses.put(key, response);
    }

    private List<String> getFormattedDateList() {
        String CURRENT_DATE = DateUtils.getCurrentDateAsFormattedString();
        String YESTERDAY_DATE = getYesterdaysDateAsFormattedString();

        List<String> yesterdayAndCurrentFormattedDateList = new ArrayList<>();
        yesterdayAndCurrentFormattedDateList.add(CURRENT_DATE);
        yesterdayAndCurrentFormattedDateList.add(YESTERDAY_DATE);

        return yesterdayAndCurrentFormattedDateList;
    }

    private void getTwoRates(String base) throws IOException {
        rates = new ArrayList<>();

        for (Map.Entry<String, Response> entry : responses.entrySet()) {
            rates.add(getRate(entry.getValue(), valueOf(base.toUpperCase(Locale.ROOT))));
            String clientName = entry.getValue().request().requestTemplate().feignTarget().name();
            log.info(clientName
                    + " send request to "
                    + serviceUrl
                    + " with now date: ");
        }
    }

    private Rate getRate(Response response, OpenExchangeRatesTickers rateType) throws IOException {
        Map<String, Object> parseResponseBodyJsonMap =
                parseResponseBody(IOUtils.toString(response.body().asInputStream()));

        return getRateFromJsonMap(parseResponseBodyJsonMap, rateType);
    }

    private Rate getRateFromJsonMap(Map<String, Object> map, OpenExchangeRatesTickers rateType) {
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
}
