package ru.alfa.app.dto.external.giphy;

import lombok.Builder;
import ru.alfa.app.dto.external.giphy.data.Data;
import ru.alfa.app.dto.external.giphy.meta.Meta;
import ru.alfa.app.dto.external.giphy.pagination.Pagination;

import java.util.List;

@lombok.Data
@Builder
public class GifsDTO {
    private List<Data> data;
    private Pagination pagination;
    private Meta meta;
}
