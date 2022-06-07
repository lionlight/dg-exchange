package ru.alfa.app.dto.giphy;

import lombok.Builder;
import ru.alfa.app.dto.giphy.data.Data;
import ru.alfa.app.dto.giphy.meta.Meta;
import ru.alfa.app.dto.giphy.pagination.Pagination;

import java.util.List;

@lombok.Data
@Builder
public class GifsDTO {
    private List<Data> data;
    private Pagination pagination;
    private Meta meta;
}
