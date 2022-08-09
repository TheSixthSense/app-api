package com.app.api.challenge.service;

import com.app.api.challenge.entity.Challenge;
import com.app.api.challenge.repository.ChallengeRepository;
import com.app.api.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallengeListByCategoryId(Long categoryId) {
        List<Challenge> challengeList = challengeRepository.findByCategoryId(categoryId);

        if (challengeList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.challenge.list.not.found").build();
        }

        return challengeList;

    }
}
