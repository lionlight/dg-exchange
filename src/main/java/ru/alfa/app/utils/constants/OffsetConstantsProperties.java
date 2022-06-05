package ru.alfa.app.utils.constants;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("static")
public class OffsetConstantsProperties {

    @Getter
    private final List<OffsetConstant> offsets;

    public OffsetConstantsProperties(List<OffsetConstant> offsets) {
        this.offsets = offsets;
    }

}