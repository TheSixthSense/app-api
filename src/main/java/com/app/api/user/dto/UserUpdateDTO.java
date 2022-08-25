package com.app.api.user.dto;

import com.app.api.common.validator.ValueOfEnum;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.VegannerStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserUpdateDTO {

    @ApiModelProperty(value = "닉네임", example = "비건첼린져_1", required = true)
    @NotEmpty
    private String nickName;

    @ApiModelProperty(value = "성별", example = "MALE", required = true)
    @NotNull
    @ValueOfEnum(enumClass = Gender.class)
    private Gender gender;

    @ApiModelProperty(value = "생년월일", example = "19920910", required = true)
    @NotEmpty
    @Pattern(regexp = "\\d{8}")
    private String birthDay;

    @ApiModelProperty(value = "비건 실천이력", example = "BEGINNER", required = true)
    @NotNull
    @ValueOfEnum(enumClass = VegannerStage.class)
    private VegannerStage vegannerStage;

}
