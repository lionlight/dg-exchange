package ru.alfa.app.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRates_Redirect() throws Exception {
        mockMvc.perform(get("/v1/rates").queryParam("base", "RUB"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("/api/v1/gifs?tag=**"));
    }

    @Test
    void getGifs() throws Exception {
        mockMvc.perform(get("/v1/gifs").queryParam("tag", Optional.of("rich").orElse("broke")))
                .andDo(print())
                .andExpect(status().isFound());
    }

}