package ru.alfa.app.dto.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Original {
    private String height;
    private String width;
    private String size;
    private String url;
    @JsonProperty("mp4_size")
    private String mp4Size;
    private String mp4;
    @JsonProperty("webp_size")
    private String webpSize;
    private String webp;
    private String frames;
    private String hash;
}
