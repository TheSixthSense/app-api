package com.app.api.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeVerifyResponseDto {

    @ApiModelProperty(value = "userChallengeId", example = "1")
    private Long userChallengeId;

    @ApiModelProperty(value = "challengeId", example = "1")
    private Long challengeId;

    // TODO: example 확인해보기
    @ApiModelProperty(value = "challengeDate", example = "2022-09-01 00:00:00.0")
    private LocalDateTime challengeDate;

    @ApiModelProperty(value = "imagePath", example = "https://")
    private String imagePath;

    @ApiModelProperty(value = "message", example = "성공을 축하드립니다. ...")
    private String message;
}
