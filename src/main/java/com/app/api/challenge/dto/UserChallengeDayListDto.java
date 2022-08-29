package com.app.api.challenge.dto;

import com.app.api.challenge.enums.ChallengeStatus;
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
public class UserChallengeDayListDto {

    @ApiModelProperty(value = "유저 챌린지 ID", example = "1", required = true)
    private long id;

    @ApiModelProperty(value = "챌린지 ID", example = "2", required = true)
    private long challengeId;

    @ApiModelProperty(value = "챌린지 날짜", example = "2022-08-30T00:00:00", required = true)
    private LocalDateTime challengeDate;

    @ApiModelProperty(value = "챌린지 인증 상태", example = "WAITING")
    private ChallengeStatus verificationStatus;

}
