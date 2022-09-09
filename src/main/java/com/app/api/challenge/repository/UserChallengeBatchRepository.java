package com.app.api.challenge.repository;

import com.app.api.challenge.entity.UserChallengeBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChallengeBatchRepository extends JpaRepository<UserChallengeBatch, Long> {

    Optional<UserChallengeBatch> findByUserChallengeId(Long userChallengeId);
}
