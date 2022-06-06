package ru.alfa.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alfa.app.client.external.ExchangeRateClient;
import ru.alfa.app.client.external.GiphyClient;
import ru.alfa.app.dto.dgexchange.DGExchangeDTO;
import ru.alfa.app.dto.dgexchange.Rate;
import ru.alfa.app.dto.giphy.GifsDTO;
import ru.alfa.app.dto.giphy.data.image.Images;
import ru.alfa.app.services.ExchangeRatesService;
import ru.alfa.app.services.GiphyService;
import ru.alfa.app.services.enums.OpenExchangeRatesTickers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/")
@Slf4j
@RequiredArgsConstructor
@Api(value = "ApiController", tags = {"ApiController"})
public class ApiController {
    private final GiphyClient giphyClient;
    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRatesService exchangeRatesService;
    private final GiphyService giphyService;
    private static final AtomicLong atomicLong = new AtomicLong();

    @ApiOperation(value = "rate from openexhcangerates api")
    @GetMapping(value = "/rates",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EntityModel<DGExchangeDTO> getRates(String base) {
        exchangeRatesService.sendRequestFromClient(exchangeRateClient);

        long timestamp = Instant.now().toEpochMilli();

        var responseYesterday = exchangeRatesService.getResponses().get("yesterday");
        var responseCurrent = exchangeRatesService.getResponses().get("current");

        var rateYesterday = exchangeRatesService.getRate(responseYesterday, OpenExchangeRatesTickers.valueOf(base));
        var rateCurrent = exchangeRatesService.getRate(responseCurrent, OpenExchangeRatesTickers.valueOf(base));

        List<Rate> rates = new ArrayList<>();

        rates.add(rateYesterday);
        rates.add(rateCurrent);

        DGExchangeDTO responseDTO = new DGExchangeDTO();
        responseDTO.setId(atomicLong.incrementAndGet());

        responseDTO.setTimestamp(timestamp);
        responseDTO.setRates(rates);

        return EntityModel.of(responseDTO,
                        linkTo(methodOn(ApiController.class).getRates(base)).withSelfRel())
                .add(Links.of(
                        Link.of("/api/v1/").withRel("all").withName("all api endpoints").withType("GET").withName("all"),
                        Link.of("/api/v1/gifs").withRel("gifs").withName("gifs endpoints").withType("GET"),
                        Link.of("/api/v1/doc").withRel("doc").withName("documentation endpoint").withType("GET")));
    }

    @ApiOperation(value = "gif from giphy api")
    @GetMapping(value = "/gifs",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Images> getGifs(@RequestParam String tag) {
        String apiKey = giphyService.getApiKey();
        int offset = giphyService.generateOffset();

        GifsDTO gif = giphyClient.getGif(apiKey, tag, offset);

        return EntityModel.of(giphyService.getGifUrl(gif),
                        linkTo(methodOn(ApiController.class).getGifs(tag)).withSelfRel())
                .add(Link.of("/api/v1/").withRel("all").withName("all api endpoints"),
                        Link.of("/api/v1/rates").withRel("rates").withName("rates endpoint").withType("GET"),
                        Link.of("/api/v1/doc").withRel("doc").withName("documentation endpoint").withType("GET"));
    }


    @ApiOperation(value = "api namespaces")
    @GetMapping
    public EntityModel<String> getNamespaces() {
        return EntityModel.of("all api endpoints",
                        linkTo(methodOn(ApiController.class).getNamespaces()).withSelfRel())
                .add(Links.of(
                        Link.of("/api/v1/rates").withRel("rates").withName("rates endpoint").withType("GET"),
                        Link.of("/api/v1/gifs").withRel("gifs").withName("gifs endpoint").withType("GET"),
                        Link.of("/api/v1/doc").withRel("doc").withName("documentation endpoint").withType("GET")));
    }

}
