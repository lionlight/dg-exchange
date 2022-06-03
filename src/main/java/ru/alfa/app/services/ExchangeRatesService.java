package ru.alfa.app.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.alfa.app.model.OpenEx;

@Service
public class ExchangeRatesService {

    @Value("${app.rate.app-id}")
    private String appId;

    @Value("${app.rate.general-base}")
    private String base;

    public boolean isNowRateGreatest(OpenEx now, OpenEx yesterday, String base) {
        return now.getRates().get(base) > yesterday.getRates().get(base);
    }

    public String getAppId() {
        return appId;
    }

    public String getGeneralBase() {
        return base;
    }
}
