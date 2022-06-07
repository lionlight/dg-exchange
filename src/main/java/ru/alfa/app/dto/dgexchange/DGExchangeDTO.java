package ru.alfa.app.dto.dgexchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DGExchangeDTO {
    private long id;
    private long timestamp;
    private List<Rate> rates;
}
