package ru.alfa.app.utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@AllArgsConstructor
@Getter
public class OffsetConstants {
    private final String name;
    private final int value;
}