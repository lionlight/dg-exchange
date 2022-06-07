package ru.alfa.app.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.alfa.app.client.external.ExchangeRateClient;
import ru.alfa.app.client.external.GiphyClient;
import ru.alfa.app.dto.dgexchange.DGExchangeDTO;
import ru.alfa.app.dto.dgexchange.Rate;
import ru.alfa.app.dto.giphy.GifsDTO;
import ru.alfa.app.dto.giphy.data.Data;
import ru.alfa.app.dto.giphy.data.image.Images;
import ru.alfa.app.dto.giphy.data.image.Original;
import ru.alfa.app.dto.openexchange.OpenExchangeDTO;
import ru.alfa.app.services.ExchangeRatesService;
import ru.alfa.app.services.GiphyService;
import ru.alfa.app.services.enums.OpenExchangeRatesTickers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
class DGExchangeIT {

    @MockBean
    private GiphyClient giphyClient;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    @MockBean
    private GiphyService giphyService;

    @MockBean
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    private ApiController apiController;

    @Test
    public void whenGetGifs_With_Tag_thenTheEntityModelShouldBeReturned() {
        Original original = Original.builder().url("").build();
        Images images = Images
                .builder().original(original).build();
        GifsDTO gifsDTO = GifsDTO.builder().data(List.of(
                        Data.builder().images(images).build()))
                .pagination(null).meta(null).build();

        when(giphyClient.getGif(giphyService.getApiKey(), "rich", giphyService.getOffset())).thenReturn(gifsDTO);
        when(giphyService.getImage(gifsDTO)).thenReturn(images);
        when(giphyService.generateRandomGifIndex()).thenReturn(0);

        var expected = apiController.getGifs("rich");
        var actual = EntityModel.of(giphyService.getImage(gifsDTO),
                        linkTo(methodOn(ApiController.class).getGifs("rich")).withSelfRel())
                .add(Link.of("/api/v1/").withRel("all").withName("all api endpoints"),
                        Link.of("/api/v1/rates").withRel("rates").withName("rates endpoint").withType("GET"),
                        Link.of("/api/v1/doc").withRel("doc").withName("documentation endpoint").withType("GET"));

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetRates_With_Base_thenTheEntityModelShouldBeReturned() {
        List<Rate> rates = new ArrayList<>();
        rates.add(Rate.builder().ticker("RUB").value(60).build());
        DGExchangeDTO responseDTO = DGExchangeDTO.builder()
                .id(0).timestamp(1).rates(rates).build();
        OpenExchangeDTO openExchangeDTOYesterday = OpenExchangeDTO.builder()
                .base("USD").rates(any()).build();
        OpenExchangeDTO openExchangeDTOCurrent = OpenExchangeDTO.builder()
                .base("USD").rates(any()).build();
        Map<String, OpenExchangeDTO> responses = new HashMap<>();
        responses.put("yesterday", openExchangeDTOYesterday);
        responses.put("current", openExchangeDTOCurrent);

        when(exchangeRatesService.getRate(openExchangeDTOYesterday, OpenExchangeRatesTickers.RUB)).thenReturn(Rate
                .builder()
                .ticker("RUB")
                .value(60)
                .build());
        when(exchangeRatesService.sendRequestFromClient(exchangeRateClient)).thenReturn(responses);
        when(exchangeRatesService.getTwoRates(responses, "RUB")).thenReturn(responseDTO);

        var expected = apiController.getRates("RUB");

        var actual = EntityModel.of(responseDTO,
                        linkTo(methodOn(ApiController.class).getRates("RUB")).withSelfRel())
                .add(Links.of(
                        Link.of("/api/v1/").withRel("all").withName("all api endpoints").withType("GET").withName("all"),
                        Link.of("/api/v1/gifs").withRel("gifs").withName("gifs endpoints").withType("GET"),
                        Link.of("/api/v1/doc").withRel("doc").withName("documentation endpoint").withType("GET")));

        assertEquals(expected, actual);
    }
}
