package ru.alfa.app.client.external;


import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfa.app.client.config.ClientConfiguration;
import ru.alfa.app.dto.openexchange.OpenExchangeDTO;

@FeignClient(
        value = "exchange-rates-client",
        url = "${app.rate.api-url}",
        configuration = ClientConfiguration.class
)
public interface ExchangeRateClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/api/historical/{date}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    OpenExchangeDTO getRates(@RequestParam String date,
                             @RequestParam("app_id") String appId,
                             @RequestParam("base") String base);
}
