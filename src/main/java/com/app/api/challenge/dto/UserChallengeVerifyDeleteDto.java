package com.app.api.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeVerifyDeleteDto {

    @ApiModelProperty(value = "userChallengeId", example = "1")
    @NotNull
    @Positive
    private Long userChallengeId;
}
