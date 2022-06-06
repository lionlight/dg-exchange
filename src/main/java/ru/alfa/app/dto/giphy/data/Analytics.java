package ru.alfa.app.dto.giphy.data;

import lombok.Data;
import ru.alfa.app.dto.giphy.data.image.OnClick;
import ru.alfa.app.dto.giphy.data.image.OnLoad;
import ru.alfa.app.dto.giphy.data.image.OnSent;

@Data
public class Analytics {
    public OnLoad onLoad;
    public OnClick onClick;
    public OnSent onSent;
}
