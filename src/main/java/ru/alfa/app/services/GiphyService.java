package ru.alfa.app.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import feign.Response;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import static ru.alfa.app.services.enums.GiphyJsonProperties.*;

@Service
@Data
public class GiphyService {

    @Value("${app.gif.api-key}")
    private String apiKey;

    @Value("${app.gif.positive-tag}")
    private String positiveTag;

    @Value("${app.gif.negative-tag}")
    private String negativeTag;

    private final int MAX_GIF_INDEX_PER_REQUEST = 49;

    public String getGifUrl(Response response) throws IOException {
        Map<String, Object> parseResponseBodyJsonMap = parseResponseBody(IOUtils.toString(response.body().asInputStream()));
        return getGifUrl(parseResponseBodyJsonMap);
    }

    public String getGifUrl(Map<String, Object> map) {
        Gson gson = new Gson();

        JsonArray jsonArrayData = gson.toJsonTree(map)
                .getAsJsonObject().get(DATA.getPropertyName()).getAsJsonArray();

        JsonObject gifAsJsonObject = jsonArrayData.get(generateRandomGifIndex())
                .getAsJsonObject().get(IMAGES.getPropertyName()).getAsJsonObject();

        return gifAsJsonObject
                .getAsJsonObject().get(ORIGINAL.getPropertyName())
                .getAsJsonObject().get(URL.getPropertyName()).getAsString();
    }

    private Map<String, Object> parseResponseBody(String body) {
        GsonJsonParser parser = new GsonJsonParser();
        return parser.parseMap(body);
    }

    private int generateRandomGifIndex() {
        int minGifIndex = 0;
        return new SecureRandom().nextInt(MAX_GIF_INDEX_PER_REQUEST - minGifIndex) + minGifIndex;
    }
}