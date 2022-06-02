package ru.alfa.app.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@Data
@ToString
public class OpenEx {
    String disclaimer;
    String license;
    String timestamp;
    String base;
    Map<String, Double> rates;
}
