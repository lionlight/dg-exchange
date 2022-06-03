package ru.alfa.app.services;

import org.springframework.boot.json.GsonJsonParser;

import java.util.Map;

public abstract class AbstractService {
    Map<String, Object> parseResponseBody(String body) {
        GsonJsonParser parser = new GsonJsonParser();
        return parser.parseMap(body);
    }
}
