package ru.alfa.app.dto.external.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedWidthDownsampled {
    private String height;
    private String width;
    private String size;
    private String url;
    @JsonProperty("webp_size")
    private String webpSize;
    private String webp;
}
