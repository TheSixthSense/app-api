package com.app.api.challenge.repository;

import com.app.api.challenge.entity.ChallengeGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeGuideRepository extends JpaRepository<ChallengeGuide, Long> {

    List<ChallengeGuide> findByChallengeIdOrderBySortAsc(Long challengeId);

}
