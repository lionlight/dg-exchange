package ru.alfa.app.dto.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DownsizedSmall {
    private String height;
    private String width;
    @JsonProperty("mp4_size")
    private String mp4Size;
    private String mp4;
}
