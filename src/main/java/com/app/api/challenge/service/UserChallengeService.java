package com.app.api.challenge.service;

import com.app.api.challenge.dto.*;
import com.app.api.challenge.entity.ChallengeSuccessNotify;
import com.app.api.challenge.entity.UserChallenge;
import com.app.api.challenge.entity.UserChallengeBatch;
import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.challenge.repository.ChallengeSuccessNotifyRepository;
import com.app.api.challenge.repository.UserChallengeBatchRepository;
import com.app.api.challenge.repository.UserChallengeRepository;
import com.app.api.common.util.DateUtil;
import com.app.api.common.util.file.FileUtil;
import com.app.api.core.exception.BizException;
import com.app.api.core.s3.NaverS3Uploader;
import com.app.api.core.s3.S3Folder;
import com.app.api.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserChallengeRepository userChallengeRepository;
    private final UserChallengeBatchRepository userChallengeBatchRepository;
    private final NaverS3Uploader naverS3Uploader;
    private final ChallengeSuccessNotifyRepository challengeSuccessNotifyRepository;

    @Transactional
    public UserChallengeVerifyResponseDto verifyUserChallenge(UserDTO userDTO,
                                                              @Validated UserChallengeVerifyRegDto userChallengeVerifyRegDto,
                                                              List<MultipartFile> multipartFileList) {
        // 정책상 이미지 업로드는 1개만 가능
        if (multipartFileList.size() != 1)
            throw BizException.withUserMessageKey("exception.user.challenge.verify.image.count").build();

        UserChallenge userChallenge = userChallengeRepository
                .findByIdAndUserId(userChallengeVerifyRegDto.getUserChallengeId(), userDTO.getId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.challenge.not.found").build());

        // multipartFileList 확장자 검사
        FileUtil.checkPermissionImageExt(multipartFileList);

        // 이미지 등록
        List<String> verificationImageList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFileList) {
            verificationImageList.add(naverS3Uploader.upload(multipartFile, S3Folder.IMAGE));
        }

        // 이미지 url challenge 에 저장하기
        userChallenge.verifyUserChallenge(verificationImageList, userChallengeVerifyRegDto.getMemo());
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
                .titleImage(challengeSuccessNotify.getTitleImage())
                .contentImage(challengeSuccessNotify.getContentImage())
                .build();
    }

    @Transactional
    public void deleteUserChallengeVerify(UserDTO userDTO, UserChallengeVerifyDeleteDto userChallengeVerifyDeleteDto) {
        UserChallenge userChallenge = userChallengeRepository
                .findByIdAndUserId(userChallengeVerifyDeleteDto.getUserChallengeId(), userDTO.getId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.challenge.not.found").build());

        userChallenge.deleteVerifyUserChallenge();
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
        long challengeId = userChallengeJoinDto.getChallengeId();
        LocalDateTime challengeDate = DateUtil.changeStringToLocalDateTime(userChallengeJoinDto.getChallengeDate());
        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

        if (challengeDate.isBefore(startOfToday)) {
            throw BizException.withUserMessageKey("exception.user.challenge.date.invalid").build();
        }
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
        LocalDateTime challengeDate = DateUtil.changeStringToLocalDateTime(date);

        return userChallengeRepository.findAllByUserIdAndChallengeDate(userId, challengeDate);
    }

    public void startUserChallengeVerifyBatch(LocalDateTime batchTime) {
        LocalDateTime batchStartOfDay = batchTime.toLocalDate().atStartOfDay();
        LocalDateTime batchEndOfDay = batchTime.toLocalDate().atTime(23,59,59,999_999_999);
        List<UserChallenge> allByChallengeDateBetween = userChallengeRepository
                .findAllByVerificationStatusAndChallengeDateBetween(ChallengeStatus.WAITING ,batchStartOfDay, batchEndOfDay);

        for (UserChallenge userChallenge : allByChallengeDateBetween) {
            UserChallengeBatch userChallengeBatch = userChallengeBatchRepository.findByUserChallengeId(userChallenge.getId())
                    .orElseGet(() ->
                            userChallengeBatchRepository.save(
                                    UserChallengeBatch.builder()
                                            .userChallengeId(userChallenge.getId())
                                            .build())
                    );
            if (userChallengeBatch.getExecutedDate() == null) {
                userChallenge.verifyFail();
                userChallengeBatch.executeBatch();
                userChallengeRepository.save(userChallenge);
                userChallengeBatchRepository.save(userChallengeBatch);
            }
        }
    }

    public List<UserChallengeMonthListDto> getChallengeMonthListByUserId(String date, Long userId) {
        // 현재 날짜로 월초~월말 구하기
        LocalDate challengeDate = DateUtil.changeStringToLocalDate(date);
        LocalDateTime startDate = challengeDate.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = challengeDate.withDayOfMonth(challengeDate.lengthOfMonth()).atTime(23, 59, 59);

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserIdAndChallengeDateBetween(userId, startDate, endDate);

        // 챌린지 목록 날짜별로 분리
        Map<String, List<UserChallenge>> userChallengeMap = userChallengeList.stream()
                .collect(Collectors.groupingBy(uc -> DateUtil.changeLocalDateTimeToString(uc.getChallengeDate())));

        List<UserChallengeMonthListDto> responseDtoList = new ArrayList<>();

        // 챌린지 성공 횟수 count
        userChallengeMap.forEach((key, value) -> {
            int successCount = 0;
            int totalCount = value.size();

            for (UserChallenge uc : value) {
                if (uc.getVerificationStatus() == ChallengeStatus.SUCCESS) {
                    successCount++;

                }
            }

            responseDtoList.add(UserChallengeMonthListDto.builder()
                    .date(key)
                    .success_count(successCount)
                    .total_count(totalCount)
                    .build());
        });

        return responseDtoList;
    }
}