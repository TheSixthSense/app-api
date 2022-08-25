package com.app.api.challenge.repository;

import com.app.api.challenge.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    Optional<UserChallenge> findById(Long id);

    List<UserChallenge> findAllByUserIdAndChallengeDateBetween(Long userId, LocalDateTime from, LocalDateTime to);
}
