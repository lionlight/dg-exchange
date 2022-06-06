package ru.alfa.app.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.alfa.app.services.DGExchangeService;
import ru.alfa.app.services.DGGifService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("dgexchange")
@Slf4j
@RequiredArgsConstructor
@Api(value = "ClientController", tags = {"ClientController"})
public class ClientController {

    private final DGExchangeService dgExchangeService;
    private final DGGifService dgGifService;

    @GetMapping("/gifs")
    public ModelAndView getGif(String code) throws Exception {

        String tag = dgExchangeService.getTag(code);

        String gifUrl = dgGifService.getGifUrl(tag);

        ModelAndView modelAndView = new ModelAndView("gifs");
        modelAndView.addObject("gif", gifUrl);

        return modelAndView;
    }
}
