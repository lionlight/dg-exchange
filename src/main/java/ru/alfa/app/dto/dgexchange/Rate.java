package ru.alfa.app.dto.dgexchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {
    @JsonProperty("timestamp")
    private String timestamp;
    private String ticker;
    private double value;
}
