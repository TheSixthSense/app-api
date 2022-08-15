package com.app.api.challenge.service;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.entity.Challenge;
import com.app.api.challenge.repository.ChallengeRepository;
import com.app.api.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public List<ChallengeListDto> getChallengeListByCategoryId(Long categoryId) {
        List<Challenge> challengeList = challengeRepository.findByCategoryId(categoryId);

        if (challengeList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.challenge.list.not.found").build();
        }

        List<ChallengeListDto> challengeListDtoList = new ArrayList<>();

        for (Challenge challenge : challengeList) {
            challengeListDtoList.add(ChallengeListDto.builder()
                    .id(challenge.getId())
                    .categoryId(challenge.getCategoryId())
                    .name(challenge.getName())
                    .description(challenge.getDescription())
                    .build());
        }

        return challengeListDtoList;

    }
}
