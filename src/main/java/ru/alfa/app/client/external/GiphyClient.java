package ru.alfa.app.client.external;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.alfa.app.client.config.ClientConfiguration;
import ru.alfa.app.dto.external.giphy.GifsDTO;

@FeignClient(
        value = "giphy-client",
        url = "${app.gif.api-url}",
        configuration = ClientConfiguration.class
)
public interface GiphyClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/v1/gifs/search")
    GifsDTO getGif(@RequestParam("api_key") String apiKey,
                   @RequestParam("q") String tag,
                   @RequestParam("offset") int offset);

}