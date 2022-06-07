package ru.alfa.app.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import feign.Response;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alfa.app.dto.giphy.GifsDTO;
import ru.alfa.app.dto.giphy.data.image.Images;
import ru.alfa.app.services.enums.GiphyJsonProperties;
import ru.alfa.app.utils.constants.OffsetConstant;
import ru.alfa.app.utils.constants.OffsetConstantsProperties;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

@Service
@Getter
public class GiphyService extends AbstractService {

    @Value("${app.gif.api-url}")
    private String serviceUrl;

    @Value("${app.gif.api-key}")
    private String apiKey;

    private final int MAX_GIF_INDEX_PER_REQUEST = 49;

    @Autowired
    private OffsetConstantsProperties offsetConstantsProperties;

    private int offset;

    public int generateOffset() {
        offset = new Random().nextInt(
                offsetConstantsProperties.getOffsets()
                        .stream()
                        .filter(o -> o.getName().equals("giphy default offset"))
                        .findFirst().orElse(new OffsetConstant("giphy minimum default", 1))
                        .getValue());

        return offset;
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

    public Images getImage(GifsDTO gifsDTO) {
        return gifsDTO.getData().get(generateRandomGifIndex()).getImages();
    }

    public int generateRandomGifIndex() {
        int minGifIndex = 0;
        return new SecureRandom().nextInt(MAX_GIF_INDEX_PER_REQUEST - minGifIndex) + minGifIndex;
    }
}