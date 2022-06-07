package ru.alfa.app.client.internal;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.alfa.app.dto.internal.dgexchange.DGExchangeDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class DGExchangeClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Getter
    private static final String URL_RATES = "http://localhost:8080/api/v1/rates";

    @Getter
    private static final String QUERY_RATES = "?base=";

    public DGExchangeDTO getRates(String code) throws URISyntaxException {
        return restTemplate.getForObject(new URI(URL_RATES + QUERY_RATES + code), DGExchangeDTO.class);
    }
}
