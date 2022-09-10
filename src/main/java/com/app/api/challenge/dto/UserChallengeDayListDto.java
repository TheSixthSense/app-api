package com.app.api.challenge.dto;

import com.app.api.challenge.enums.ChallengeStatus;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class UserChallengeDayListDto {

    @ApiModelProperty(value = "유저 챌린지 ID", example = "1")
    private long id;

    @ApiModelProperty(value = "유저 ID", example = "1")
    private long userId;

    @ApiModelProperty(value = "챌린지 ID", example = "2")
    private long challengeId;

    @ApiModelProperty(value = "챌린지 날짜", example = "2022-08-30T00:00:00")
    private LocalDateTime challengeDate;

    @ApiModelProperty(value = "챌린지명", example = "우유 대신 두유 먹기")
    private String name;

    @ApiModelProperty(value = "챌린지 이모지", example = "\uD83C\uDF3E")
    private String emoji;

    @ApiModelProperty(value = "챌린지 인증 상태", example = "WAITING")
    private ChallengeStatus verificationStatus;

    @ApiModelProperty(value = "챌린지 인증 날짜", example = "2022-08-30T00:00:00")
    private LocalDateTime verificationDate;

    @ApiModelProperty(value = "챌린지 인증 메모", example = "챌린지 인증 메모...ㅎㅎ")
    private String verificationMemo;

    @ApiModelProperty(value = "챌린지 인증 사진", example = "https://...")
    private String verificationImage;

    @QueryProjection
    @Builder
    public UserChallengeDayListDto(
            long id, long userId, long challengeId, LocalDateTime challengeDate, String name, String emoji,
            ChallengeStatus verificationStatus, LocalDateTime verificationDate, String verificationMemo,
            String verificationImage) {
        this.id = id;
        this.userId = userId;
        this.challengeId = challengeId;
        this.challengeDate = challengeDate;
        this.name = name;
        this.emoji = emoji;
        this.verificationStatus = verificationStatus;
        this.verificationDate = verificationDate;
        this.verificationMemo = verificationMemo;
        this.verificationImage = verificationImage;
    }
}

