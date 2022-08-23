package com.app.api.challenge.service;

import com.app.api.challenge.dto.UserChallengeJoinDto;
import com.app.api.challenge.entity.UserChallenge;
import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.challenge.repository.UserChallengeRepository;
import com.app.api.core.exception.BizException;
import com.app.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserRepository userRepository;
    private final UserChallengeRepository userChallengeRepository;

    public void joinChallenge(UserChallengeJoinDto userChallengeJoinDto, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() ->
                        BizException.withUserMessage("exception.user.not.found").build());

        long challengeId = userChallengeJoinDto.getChallengeId();
        LocalDateTime challengeDate = userChallengeJoinDto.getChallengeDate();

        // TODO
        Optional<UserChallenge> userChallenge = userChallengeRepository.findByUserIdAndChallengeIdAndChallengeDate(userId, challengeId, challengeDate);
        userChallenge.ifPresent(user -> BizException.withUserMessage("exception.challenge.join.already").build());

        userChallengeRepository.save(UserChallenge.builder()
                .userId(userId)
                .challengeId(challengeId)
                .challengeDate(challengeDate)
                .verificationStatus(ChallengeStatus.WAITING)
                .build());

    }
}
