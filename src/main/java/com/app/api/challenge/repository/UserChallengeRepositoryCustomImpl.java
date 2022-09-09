package com.app.api.challenge.repository;

import com.app.api.challenge.dto.QUserChallengeDayListDto;
import com.app.api.challenge.dto.UserChallengeDayListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.api.challenge.entity.QUserChallenge.userChallenge;
import static com.app.api.challenge.entity.QChallenge.challenge;

@RequiredArgsConstructor
public class UserChallengeRepositoryCustomImpl implements UserChallengeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserChallengeDayListDto> findAllByUserIdAndChallengeDate(Long userId, LocalDateTime challengeDate) {
        return queryFactory.select(new QUserChallengeDayListDto(
                userChallenge.id,
                userChallenge.challengeId,
                challenge.name,
                challenge.emoji,
                userChallenge.challengeDate,
                userChallenge.verificationStatus
        )).from(userChallenge)
                .innerJoin(challenge).on(userChallenge.challengeId.eq(challenge.id))
                .where(userChallenge.userId.eq(userId),
                        userChallenge.challengeDate.eq(challengeDate))
                .fetch();
    }
}
