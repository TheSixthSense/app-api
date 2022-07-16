package com.app.api.common.validator.dto;

import com.app.api.common.validator.ValueOfEnum;
import com.app.api.common.validator.enums.TestEnum;

public class TestDTO {
    @ValueOfEnum(enumClass = TestEnum.class)
    private TestEnum testEnum;

    public TestEnum getTestEnum() {
        return testEnum;
    }

    public void setTestEnum(TestEnum testEnum) {
        this.testEnum = testEnum;
    }
}
