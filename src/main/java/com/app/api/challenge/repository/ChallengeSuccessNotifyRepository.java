package com.app.api.challenge.repository;

import com.app.api.challenge.entity.ChallengeSuccessNotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeSuccessNotifyRepository extends JpaRepository<ChallengeSuccessNotify, Long> {

    List<ChallengeSuccessNotify> findAllByChallengeId(Long challengeId);

}
