package com.app.api.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChallengeStatsDto {

    private int totalCount;

    private int successCount;

    private int waitingCount;

}
