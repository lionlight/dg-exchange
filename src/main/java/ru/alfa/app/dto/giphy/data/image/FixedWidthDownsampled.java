package ru.alfa.app.dto.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FixedWidthDownsampled {
    private String height;
    private String width;
    private String size;
    private String url;
    @JsonProperty("webp_size")
    private String webpSize;
    private String webp;
}
