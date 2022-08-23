package com.app.api.challenge.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeJoinDto {

    @ApiModelProperty(value = "챌린지 ID", example = "1", required = true)
    @Min(1)
    private long challengeId;

    @ApiModelProperty(value = "챌린지 날짜", example = "2022-08-30T00:00:00", required = true)
    private LocalDateTime challengeDate;

}
