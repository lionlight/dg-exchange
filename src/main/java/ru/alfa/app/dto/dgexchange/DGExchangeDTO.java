package ru.alfa.app.dto.dgexchange;

import lombok.Data;

import java.util.List;

@Data
public class DGExchangeDTO {
    private long id;
    private long timestamp;
    private List<Rate> rates;
}
