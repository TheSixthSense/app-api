package com.app.api.challenge.service;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.entity.Challenge;
import com.app.api.challenge.repository.ChallengeRepository;
import com.app.api.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ModelMapper modelMapper;
    private final ChallengeRepository challengeRepository;

    public List<ChallengeListDto> getChallengeListByCategoryId(Long categoryId) {
        List<Challenge> challengeList = challengeRepository.findByCategoryId(categoryId);

        if (challengeList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.challenge.list.not.found").build();
        }

        List<ChallengeListDto> challengeListDtoList = challengeList.stream()
                .map(challenge -> modelMapper.map(challenge, ChallengeListDto.class))
                .collect(Collectors.toList());

        return challengeListDtoList;
    }

    public List<ChallengeListDto> getChallengeList() {
        List<Challenge> challengeList = challengeRepository.findAllByOrderByCategoryIdAscSortAsc();

        if (challengeList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.challenge.total.list.not.found").build();
        }

        List<ChallengeListDto> challengeListDtoList = challengeList.stream()
                .map(challenge -> modelMapper.map(challenge, ChallengeListDto.class))
                .collect(Collectors.toList());

        return challengeListDtoList;

    }
}
