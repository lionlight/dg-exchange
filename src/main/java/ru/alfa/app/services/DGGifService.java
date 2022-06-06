package ru.alfa.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfa.app.client.internal.DGGifClient;
import ru.alfa.app.dto.giphy.data.image.Images;

import java.net.URISyntaxException;

@Service
public class DGGifService {

    @Autowired
    DGGifClient client;

    public String getGifUrl(String tag) throws URISyntaxException {
        Images gif = client.getGif(tag);
        return gif.getOriginal().getUrl();
    }

}
