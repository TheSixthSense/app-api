package com.app.api.challenge.repository;

import com.app.api.challenge.dto.UserChallengeDayListDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UserChallengeRepositoryCustom {
    List<UserChallengeDayListDto> findAllByUserIdAndChallengeDate(Long userId, LocalDateTime challengeDate);
}
