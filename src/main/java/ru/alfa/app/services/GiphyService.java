package ru.alfa.app.services;

import com.google.gson.internal.LinkedTreeMap;
import feign.Response;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

@Service
@Data
public class GiphyService {

    @Value("${app.gif.api-key}")
    private String apiKey;

    @Value("${app.gif.positive-tag}")
    private String positiveTag;

    @Value("${app.gif.negative-tag}")
    private String negativeTag;

    public String mappingToGifUrl(Response response) {
        ArrayList<LinkedTreeMap<String, Object>> dataMap = parse(response);
        int randomGifIndex = generateRandomInt(49, 0);
        try {
            LinkedTreeMap<String, Object> imagesMap = (LinkedTreeMap<String, Object>) dataMap.get(randomGifIndex).get("images");
            LinkedTreeMap<String, Object> originalGifMap = (LinkedTreeMap<String, Object>) imagesMap.get("original");
            return (String) originalGifMap.get("url");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return "/";
    }

    private ArrayList<LinkedTreeMap<String, Object>> parse(Response response) {
        GsonJsonParser parser = new GsonJsonParser();

        Map<String, Object> jsonObjectMap = parser.parseMap(response.body().toString());

        return (ArrayList<LinkedTreeMap<String, Object>>) jsonObjectMap.get("data");
    }

    private int generateRandomInt(int max, int min) {
        int maxGifIndex = max;
        int minGifIndex = min;
        return new SecureRandom().nextInt(maxGifIndex - minGifIndex) + minGifIndex;
    }
}