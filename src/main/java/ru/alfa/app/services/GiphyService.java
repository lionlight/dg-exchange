package ru.alfa.app.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import feign.Response;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.services.enums.GiphyJsonProperties;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Map;

@Service
@Getter
public class GiphyService extends AbstractService {

    @Value("${app.gif.api-url}")
    private String serviceUrl;

    @Value("${app.gif.api-key}")
    private String apiKey;

    @Value("${app.gif.positive-tag}")
    private String positiveTag;

    @Value("${app.gif.negative-tag}")
    private String negativeTag;

    private final int MAX_GIF_INDEX_PER_REQUEST = 49;

    public String getGifUrl(Response response) throws IOException {
        Map<String, Object> parseResponseBodyJsonMap;
        try (InputStream bodyInputStream = response.body().asInputStream()) {
            parseResponseBodyJsonMap = parseResponseBody(IOUtils.toString(bodyInputStream));
        }
        if (parseResponseBodyJsonMap != null) {
            return getGifUrl(parseResponseBodyJsonMap);
        }
        return "v1/error";
    }

    public String getGifUrl(Map<String, Object> map) {
        Gson gson = new Gson();

        JsonArray jsonArrayData = gson.toJsonTree(map)
                .getAsJsonObject().get(GiphyJsonProperties.DATA.getPropertyName()).getAsJsonArray();

        JsonObject gifAsJsonObject = jsonArrayData.get(generateRandomGifIndex())
                .getAsJsonObject().get(GiphyJsonProperties.IMAGES.getPropertyName()).getAsJsonObject();

        return gifAsJsonObject
                .getAsJsonObject().get(GiphyJsonProperties.ORIGINAL.getPropertyName())
                .getAsJsonObject().get(GiphyJsonProperties.URL.getPropertyName()).getAsString();
    }

    private int generateRandomGifIndex() {
        int minGifIndex = 0;
        return new SecureRandom().nextInt(MAX_GIF_INDEX_PER_REQUEST - minGifIndex) + minGifIndex;
    }
}