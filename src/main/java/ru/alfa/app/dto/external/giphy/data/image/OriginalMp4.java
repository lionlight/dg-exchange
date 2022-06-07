package ru.alfa.app.dto.external.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalMp4 {
    private String height;
    private String width;
    @JsonProperty("mp4_size")
    private String mp4Size;
    private String mp4;
}
