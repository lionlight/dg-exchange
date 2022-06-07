package ru.alfa.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfa.app.client.internal.DGGifClient;
import ru.alfa.app.dto.internal.dgexchange.DGGifDTO;

import java.net.URISyntaxException;

@Service
public class DGGifService {

    @Autowired
    DGGifClient client;

    public DGGifDTO getGifUrl(String tag) throws URISyntaxException {
        return client.getGif(tag);
    }

}
