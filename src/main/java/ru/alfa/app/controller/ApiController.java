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
import ru.alfa.app.dto.giphy.GifsDTO;
import ru.alfa.app.dto.giphy.data.image.Images;
import ru.alfa.app.dto.openexchange.OpenExchangeDTO;
import ru.alfa.app.services.ExchangeRatesService;
import ru.alfa.app.services.GiphyService;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/")
@Slf4j
@RequiredArgsConstructor
@Api(value = "ApiController", tags = {"ApiController"})
public class ApiController {

    public static String URL = "/api/v1";
    private final GiphyClient giphyClient;
    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRatesService exchangeRatesService;
    private final GiphyService giphyService;

    @ApiOperation(value = "rate from openexhcangerates api")
    @GetMapping(value = "/rates",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public EntityModel<DGExchangeDTO> getRates(String base) {
        Map<String, OpenExchangeDTO> responses = exchangeRatesService.sendRequestFromClient(exchangeRateClient);

        DGExchangeDTO responseDTO = exchangeRatesService.getTwoRates(responses, base);

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

        return EntityModel.of(giphyService.getImage(gif),
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
