package ru.alfa.app.utils.constants;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class OffsetConstantPropertiesTest {

    @Autowired
    private OffsetConstantsProperties offsetConstantsProperties;

    @Test
    void getOffsets() {
        assertThat(offsetConstantsProperties.getOffsets()).hasSize(2);
    }
}