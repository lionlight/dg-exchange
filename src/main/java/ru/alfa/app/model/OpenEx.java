package ru.alfa.app.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
@ToString
public class OpenEx {
    String disclaimer;
    String license;
    String timestamp;
    String base;
    Map<String, Double> rates;
}
