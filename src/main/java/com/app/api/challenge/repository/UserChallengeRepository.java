package com.app.api.challenge.repository;

import com.app.api.challenge.dto.UserChallengeDayListDto;
import com.app.api.challenge.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long>, UserChallengeRepositoryCustom {

    Optional<UserChallenge> findById(Long id);

    Optional<UserChallenge> findByIdAndUserId(Long id, Long userId);

    List<UserChallenge> findAllByUserIdAndChallengeDateBetween(Long userId, LocalDateTime from, LocalDateTime to);

    @Transactional
    @Modifying
    @Query(value = "delete from UserChallenge uc where uc.userId = :userId\n")
    void deleteAllByUserIdInQuery(@Param("userId") Long userId);

    Optional<UserChallenge> findByUserIdAndChallengeIdAndChallengeDate(Long userId, Long challengeId, LocalDateTime challengeDate);

}
