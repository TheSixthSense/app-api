package com.app.api.challenge.repository;

import com.app.api.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeGuideRepository extends JpaRepository<Challenge, Long> {

}
