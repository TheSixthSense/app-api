package com.app.api.common.validator;

import com.app.api.common.validator.dto.TestDTO;
import com.app.api.common.validator.enums.TestEnum;
import com.app.api.config.BaseValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("@ValueOfEnum test")
class ValueOfEnumValidatorTest extends BaseValidatorTest {

    private TestDTO testDto;

    @BeforeEach
    void setUp() {
        testDto = new TestDTO();
    }

    @Order(1)
    @Test
    @DisplayName("정상 작동확인")
    public void ok() {
        testDto.setTestEnum(TestEnum.TEST);

        Set<ConstraintViolation<TestDTO>> validate = validator.validate(testDto);
        assertThat(validate).isEmpty();
    }

    @Order(2)
    @Test
    @DisplayName("value 값이 Null일 경우 예외를 발생시키지 않음")
    public void ok_when_enum_value_null() {
        testDto.setTestEnum(null);

        Set<ConstraintViolation<TestDTO>> validate = validator.validate(testDto);
        assertThat(validate).isEmpty();
    }
}
