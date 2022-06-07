package ru.alfa.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.client.internal.DGExchangeClient;
import ru.alfa.app.dto.internal.dgexchange.DGExchangeDTO;
import ru.alfa.app.dto.internal.dgexchange.Rate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DGExchangeService {

    @Autowired
    DGExchangeClient client;

    @Value("${app.gif.positive-tag}")
    private String positiveTag;

    @Value("${app.gif.negative-tag}")
    private String negativeTag;

    public double getValues(Rate rate) {
        return rate.getValue();
    }

    public int compareFromCode(String code) throws URISyntaxException {
        DGExchangeDTO dgExchangeDTO = client.getRates(code);

        List<Double> rateValues = new ArrayList<>();

        for (Rate rate : dgExchangeDTO.getRates()) {
            rateValues.add(rate.getValue());
        }

        if (rateValues.isEmpty()) {
            return -1;
        }

        if (rateValues.get(0) > rateValues.get(1)) {
            return 1;
        } else return 0;
    }

    public String getTag(String code) throws Exception {
        int compare = compareFromCode(code);

        if (compare == -1) {
            throw new Exception();
        }

        if (compare == 1) {
            return positiveTag;
        } else {
            return negativeTag;
        }
    }
}
