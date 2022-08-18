package com.app.api.challenge.service;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.repository.ChallengeGuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeGuideService {

    private final ChallengeGuideRepository challengeGuideRepository;

    public List<ChallengeListDto> getChallengeGuideByChallengeId(Long challengeId) {

        return ;
    }
}
