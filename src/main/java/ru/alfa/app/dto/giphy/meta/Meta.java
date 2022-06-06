package ru.alfa.app.dto.giphy.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meta {
    private String status;
    private String msg;
    @JsonProperty("response_id")
    private String responseId;
}
