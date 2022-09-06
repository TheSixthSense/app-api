package com.app.api.challenge.dto;

import com.app.api.challenge.enums.ChallengeStatus;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeMonthListDto {

    @ApiModelProperty(value = "챌린지 날짜", example = "2022-09-01")
    private String date;

    @ApiModelProperty(value = "챌린지 성공 횟수", example = "1")
    private int success_count;

    @ApiModelProperty(value = "챌린지 토탈 횟수", example = "2")
    private int total_count;
}

