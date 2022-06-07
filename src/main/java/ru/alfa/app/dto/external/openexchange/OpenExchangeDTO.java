package ru.alfa.app.dto.external.openexchange;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class OpenExchangeDTO {
    private String disclaimer;
    private String license;
    private String timestamp;
    private String base;
    private Map<String, Double> rates;
}
