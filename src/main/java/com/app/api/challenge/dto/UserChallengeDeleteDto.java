package com.app.api.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeDeleteDto {

    @ApiModelProperty(value = "유저 챌린지 ID", example = "1", required = true)
    @Positive
    private long userChallengeId;
}
