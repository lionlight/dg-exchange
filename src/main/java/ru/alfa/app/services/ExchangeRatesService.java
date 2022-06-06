package ru.alfa.app.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.client.external.ExchangeRateClient;
import ru.alfa.app.dto.dgexchange.Rate;
import ru.alfa.app.dto.openexchange.OpenExchangeDTO;
import ru.alfa.app.services.enums.OpenExchangeRatesTickers;
import ru.alfa.app.utils.DateUtils;

import java.io.IOException;
import java.util.*;

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
    private Map<String, OpenExchangeDTO> responses;
    private List<Rate> rates;

    public void sendRequestFromClient(final ExchangeRateClient client) {
        responses = new HashMap<>();
        List<String> dates = getFormattedDateList();

        final String current = "current";
        final String yesterday = "yesterday";

        for (int i = 0; i < dates.size(); i++) {
            final String key = i == 0 ? current : yesterday; //two requests (current, yesterday)
            send(client, dates.get(i), key);
        }
    }

    public boolean compareTwoRates(String base) {
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

    private void getTwoRates(String base) {
        rates = new ArrayList<>();

        for (Map.Entry<String, OpenExchangeDTO> entry : responses.entrySet()) {
            rates.add(getRate(entry.getValue(), valueOf(base.toUpperCase(Locale.ROOT))));
        }
    }

    public Rate getRate(OpenExchangeDTO response, OpenExchangeRatesTickers rateType) {
        return getRateFromJson(response, rateType);
    }

    private Rate getRateFromJson(OpenExchangeDTO response, OpenExchangeRatesTickers rateType) {
        Rate rate = new Rate();
        rate.setTimestamp(response.getTimestamp());
        rate.setTicker(rateType.getRateTypeName());
        rate.setValue(response.getRates().get(String.valueOf(rateType)));
        return rate;
    }
}
