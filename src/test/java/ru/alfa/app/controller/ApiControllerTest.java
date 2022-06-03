package ru.alfa.app.controller;


import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RequiredArgsConstructor
//@SpringBootTest(classes = {Application.class},
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@ComponentScan(basePackages = {"ru.alfa.app.controller", "ru.alfa.services", "ru.alfa.client"})
class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRates() throws Exception {
        AssertionsForClassTypes.assertThat(mockMvc).isNotNull();
        AssertionsForClassTypes.assertThat(apiController).isNotNull();

        mockMvc.perform(get("/v1/rates").queryParam("base", "RUB"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrlTemplate("/api/v1/gifs?tag=broke"));
    }

    @Test
    void getGifs() throws Exception {
        AssertionsForClassTypes.assertThat(mockMvc).isNotNull();
        AssertionsForClassTypes.assertThat(apiController).isNotNull();
        mockMvc.perform(get("/v1/gifs").queryParam("tag", Optional.of("rich").orElse("broke")))
                .andDo(print())
                .andExpect(status().isFound());
    }

}