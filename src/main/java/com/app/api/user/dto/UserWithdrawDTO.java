package com.app.api.user.dto;

import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class UserWithdrawDTO {

    @ApiModelProperty(value = "id", example = "1")
    private Long id;

    @ApiModelProperty(value = "Apple 계정 정보", example = "001805.7d48278a5f8d4c618263bef5a616f7dc.1512")
    private String appleId;

    @ApiModelProperty(value = "email", example = "vegganer@gmail.com")
    private String email;

    @ApiModelProperty(value = "닉네임", example = "비건첼린져_1")
    private String nickName;

    @ApiModelProperty(value = "성별", example = "MALE")
    private Gender gender;

    @ApiModelProperty(value = "생년월일", example = "19920910")
    private String birthDay;

    @ApiModelProperty(value = "비건 실천이력", example = "BEGINNER")
    private VegannerStage vegannerStage;

    @ApiModelProperty(value = "계정권한", example = "USER")
    private UserRoleType userRoleType;

    @ApiModelProperty(value = "생성날짜", example = "2022-07-10T00:55:00.181448")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "수정날짜", example = "2022-07-10T00:55:00.181448")
    private LocalDateTime updatedDate;
}
