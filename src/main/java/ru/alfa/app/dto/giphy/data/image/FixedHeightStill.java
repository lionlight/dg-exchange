package ru.alfa.app.dto.giphy.data.image;

import lombok.Data;

@Data
public class FixedHeightStill {
    private String height;
    private String width;
    private String size;
    private String url;
}