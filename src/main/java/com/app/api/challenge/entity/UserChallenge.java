package com.app.api.challenge.entity;

import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.common.entity.BaseTimeEntity;
import com.app.api.core.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_challenge")
public class UserChallenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long challengeId;

    @Column(nullable = false)
    private LocalDateTime challengeDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeStatus verificationStatus;

    @Column
    private LocalDateTime verificationDate;

    @Column(length = 100)
    private String verificationMemo;

    @Column
    private String verificationImage;

    public void verifyUserChallenge(List<String> verificationImageList, String memo) throws BizException {
        LocalDate now = LocalDate.now();
        LocalDateTime todayStartTime = now.atStartOfDay();

        // 현재 정책상의 이유로 이미지 업로드는 1개만 가능
        if (verificationImageList.size() != 1)
            throw BizException.withUserMessageKey("exception.user.challenge.verify.image.count").build();

        if (this.challengeDate.isAfter(todayStartTime))
            this.verificationStatus = ChallengeStatus.SUCCESS;
        else
            throw BizException.withUserMessageKey("exception.user.challenge.verify.over.time").build();

        for (String imagePath : verificationImageList) {
            this.verificationImage = imagePath;
        }

        this.verificationMemo = memo;
    }

    public void deleteVerifyUserChallenge() {
        LocalDate now = LocalDate.now();
        LocalDateTime todayStartTime = now.atStartOfDay();

        if (this.challengeDate.isAfter(todayStartTime))
            this.verificationStatus = ChallengeStatus.WAITING;
        else
            this.verificationStatus = ChallengeStatus.FAIL;

        this.verificationImage = null;
        this.verificationMemo = null;
    }

}
