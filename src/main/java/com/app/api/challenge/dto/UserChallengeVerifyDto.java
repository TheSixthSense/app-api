package com.app.api.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeVerifyDto {

    @ApiModelProperty(value = "userChallengeId", example = "1")
    @NotNull
    @Positive
    private Long userChallengeId;

    // TODO: size 100자 맞는지 테스트 해봐야 한다.
    @ApiModelProperty(value = "memo", example = "오늘의 점심! 후무스 샐러드로 대체함! 존맛탱")
    @NotEmpty
    @Size(max = 100)
    private String memo;
}
