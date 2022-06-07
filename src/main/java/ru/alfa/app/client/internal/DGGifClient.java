package ru.alfa.app.client.internal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.alfa.app.dto.internal.dgexchange.DGGifDTO;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class DGGifClient {
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL_RATES = "http://localhost:8080/api/v1/gifs";
    private static final String QUERY_RATES = "?tag=";

    public DGGifDTO getGif(String tag) throws URISyntaxException {
        return restTemplate.getForObject(new URI(URL_RATES + QUERY_RATES + tag), DGGifDTO.class);

    }
}
