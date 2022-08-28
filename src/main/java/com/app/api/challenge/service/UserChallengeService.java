package com.app.api.challenge.service;

import com.app.api.challenge.dto.*;
import com.app.api.challenge.entity.UserChallenge;
import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.challenge.repository.UserChallengeRepository;
import com.app.api.common.util.DateUtil;
import com.app.api.common.util.file.FileUtil;
import com.app.api.core.exception.BizException;
import com.app.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.app.api.challenge.entity.ChallengeSuccessNotify;
import com.app.api.challenge.repository.ChallengeSuccessNotifyRepository;
import com.app.api.core.s3.NaverS3Uploader;
import com.app.api.core.s3.S3Folder;
import com.app.api.user.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserChallengeRepository userChallengeRepository;
    private final NaverS3Uploader naverS3Uploader;
    private final ChallengeSuccessNotifyRepository challengeSuccessNotifyRepository;

    @Transactional
    public UserChallengeVerifyResponseDto verifyUserChallenge(UserChallengeVerifyDto userChallengeVerifyDto,
                                                              List<MultipartFile> multipartFileList) {
        // 정책상 이미지 업로드는 1개만 가능
        if (multipartFileList.size() != 1)
            throw BizException.withUserMessageKey("exception.user.challenge.verify.image.count").build();

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeVerifyDto.getUserChallengeId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.challenge.not.found").build());

        // multipartFileList 확장자 검사
        FileUtil.checkPermissionImageExt(multipartFileList);

        // 이미지 등록
        List<String> verificationImageList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            verificationImageList.add(naverS3Uploader.upload(multipartFile, S3Folder.IMAGE));
        }

        // 이미지 url challenge 에 저장하기
        userChallenge.updateVerifyInfo(verificationImageList, userChallengeVerifyDto.getMemo());
        userChallengeRepository.save(userChallenge);

        // 인증성공 메세지를 랜덤으로 지정
        List<ChallengeSuccessNotify> challengeSuccessNotifyList = challengeSuccessNotifyRepository
                .findAllByChallengeId(userChallenge.getChallengeId());

        if (challengeSuccessNotifyList.isEmpty())
            throw BizException.withUserMessageKey("exception.user.challenge.success.notify.not.found").build();

        int index = (int) (Math.random() * challengeSuccessNotifyList.size());
        ChallengeSuccessNotify challengeSuccessNotify = challengeSuccessNotifyList.get(index);

        // ResponseDTO
        return UserChallengeVerifyResponseDto.builder()
                .userChallengeId(userChallenge.getChallengeId())
                .challengeId(userChallenge.getChallengeId())
                .challengeDate(userChallenge.getChallengeDate())
                .imagePath(challengeSuccessNotify.getImagePath())
                .message(challengeSuccessNotify.getMessage())
                .build();
    }

    /**
     * 사용자 챌린지 통계
     */
    @Transactional(readOnly = true)
    public UserChallengeStatsDto getUserStats(UserDTO userDTO) {
        LocalDate now = LocalDate.now();
        LocalDateTime from = now.withDayOfMonth(1).atStartOfDay();
        LocalDateTime to = now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserIdAndChallengeDateBetween(userDTO.getId(), from, to);

        int totalCount = userChallengeList.size();
        int successCount = 0;
        int waitingCount = 0;
        for (UserChallenge userChallenge : userChallengeList) {
            if (userChallenge.getVerificationStatus() == ChallengeStatus.SUCCESS)
                successCount++;
            else if (userChallenge.getVerificationStatus() == ChallengeStatus.WAITING)
                waitingCount++;
        }

        return UserChallengeStatsDto.builder()
                .totalCount(totalCount)
                .successCount(successCount)
                .waitingCount(waitingCount)
                .build();
    }

    public void joinChallenge(UserChallengeJoinDto userChallengeJoinDto, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() ->
                        BizException.withUserMessageKey("exception.user.not.found").build());

        long challengeId = userChallengeJoinDto.getChallengeId();
        LocalDateTime challengeDate = userChallengeJoinDto.getChallengeDate();

        userChallengeRepository.findByUserIdAndChallengeIdAndChallengeDate(userId, challengeId, challengeDate)
                .ifPresent(user -> {
                    throw BizException.withUserMessageKey("exception.user.challenge.join.already").build();
                });

        userChallengeRepository.save(UserChallenge.builder()
                .userId(userId)
                .challengeId(challengeId)
                .challengeDate(challengeDate)
                .verificationStatus(ChallengeStatus.WAITING)
                .build());
    }

    public List<UserChallengeDayListDto> getChallengeListByUserId(String date, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.not.found").build());

        LocalDateTime challengeDate = DateUtil.changeStringToLocalDateTime(date);

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserIdAndChallengeDate(userId, challengeDate);

        List<UserChallengeDayListDto> userChallengeDayList = userChallengeList.stream()
                .map(userChallenge -> modelMapper.map(userChallenge, UserChallengeDayListDto.class))
                .collect(Collectors.toList());

        return userChallengeDayList;
    }
}