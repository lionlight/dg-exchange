package ru.alfa.app.dto.external.giphy.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pagination {
    @JsonProperty("total_count")
    private String totalCount;
    private String count;
    private String offset;
}
