package ru.alfa.app.dto.external.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedWidthSmall {
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
}
