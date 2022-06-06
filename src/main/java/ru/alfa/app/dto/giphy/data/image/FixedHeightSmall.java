package ru.alfa.app.dto.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FixedHeightSmall {
    private String height;
    private String width;
    private String size;
    private String url;
    @JsonProperty("mp4_size")
    private String mp4Size;
    @JsonProperty("webp_size")
    private String webpSize;
    private String webp;
}
