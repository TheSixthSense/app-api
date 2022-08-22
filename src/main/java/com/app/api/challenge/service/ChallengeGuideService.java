package com.app.api.challenge.service;

import com.app.api.challenge.dto.ChallengeGuideListDto;
import com.app.api.challenge.entity.ChallengeGuide;
import com.app.api.challenge.repository.ChallengeGuideRepository;
import com.app.api.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeGuideService {

    private final ModelMapper modelMapper;
    private final ChallengeGuideRepository challengeGuideRepository;

    public List<ChallengeGuideListDto> getChallengeGuideByChallengeId(Long challengeId) {
        List<ChallengeGuide> guideList = challengeGuideRepository.findByChallengeIdOrderBySortAsc(challengeId);

        if (guideList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.challenge.guide.list.not.found").build();
        }

        List<ChallengeGuideListDto> guideListDtoList = guideList.stream()
                .map(guide -> modelMapper.map(guide, ChallengeGuideListDto.class))
                .collect(Collectors.toList());

        return guideListDtoList;
    }
}
