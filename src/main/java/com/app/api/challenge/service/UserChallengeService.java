package com.app.api.challenge.service;

import com.app.api.challenge.dto.UserChallengeStatsDto;
import com.app.api.challenge.dto.UserChallengeVerifyDeleteDto;
import com.app.api.challenge.dto.UserChallengeVerifyRegDto;
import com.app.api.challenge.dto.UserChallengeVerifyResponseDto;
import com.app.api.challenge.entity.ChallengeSuccessNotify;
import com.app.api.challenge.entity.UserChallenge;
import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.challenge.repository.ChallengeSuccessNotifyRepository;
import com.app.api.challenge.repository.UserChallengeRepository;
import com.app.api.common.util.file.FileUtil;
import com.app.api.core.exception.BizException;
import com.app.api.core.s3.NaverS3Uploader;
import com.app.api.core.s3.S3Folder;
import com.app.api.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChallengeService {

    private final UserChallengeRepository userChallengeRepository;
    private final NaverS3Uploader naverS3Uploader;
    private final ChallengeSuccessNotifyRepository challengeSuccessNotifyRepository;

    @Transactional
    public UserChallengeVerifyResponseDto verifyUserChallenge(UserChallengeVerifyRegDto userChallengeVerifyRegDto,
                                                              List<MultipartFile> multipartFileList) {
        // 정책상 이미지 업로드는 1개만 가능
        if (multipartFileList.size() != 1)
            throw BizException.withUserMessageKey("exception.user.challenge.verify.image.count").build();

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeVerifyRegDto.getUserChallengeId())
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
                .imagePath(challengeSuccessNotify.getImagePath())
                .message(challengeSuccessNotify.getMessage())
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
}
