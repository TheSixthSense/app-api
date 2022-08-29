package com.app.api.challenge.dto;

import com.app.api.challenge.enums.ChallengeStatus;
import com.querydsl.core.annotations.QueryProjection;
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
public class UserChallengeDayListDto {

    @ApiModelProperty(value = "유저 챌린지 ID", example = "1", required = true)
    private long id;

    @ApiModelProperty(value = "챌린지 ID", example = "2", required = true)
    private long challengeId;

    @ApiModelProperty(value = "챌린지명", example = "우유 대신 두유 먹기", required = true)
    private String name;

    @ApiModelProperty(value = "챌린지 이모지", example = "\uD83C\uDF3E")
    private String emoji;

    @ApiModelProperty(value = "챌린지 날짜", example = "2022-08-30T00:00:00", required = true)
    private LocalDateTime challengeDate;

    @ApiModelProperty(value = "챌린지 인증 상태", example = "WAITING")
    private ChallengeStatus verificationStatus;

    @QueryProjection
    @Builder
    public UserChallengeDayListDto(long id, long challengeId, String name, String emoji, LocalDateTime challengeDate, ChallengeStatus verificationStatus) {
        this.id = id;
        this.challengeId = challengeId;
        this.name = name;
        this.emoji = emoji;
        this.challengeDate = challengeDate;
        this.verificationStatus = verificationStatus;
    }
}

