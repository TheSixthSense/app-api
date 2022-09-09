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
import java.time.LocalTime;
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

    @Column(length = 200)
    private String verificationMemo;

    @Column
    private String verificationImage;

    public void verifyUserChallenge(List<String> verificationImageList, String memo) throws BizException {
        LocalDate now = LocalDate.now();

        // 현재 정책상의 이유로 이미지 업로드는 1개만 가능
        if (verificationImageList.size() != 1)
            throw BizException.withUserMessageKey("exception.user.challenge.verify.image.count").build();

        // 챌린지 인증은 당일에만 가능
        if (this.challengeDate.toLocalDate().equals(now))
            this.verificationStatus = ChallengeStatus.SUCCESS;
        else
            throw BizException.withUserMessageKey("exception.user.challenge.verify.must.be.today").build();

        for (String imagePath : verificationImageList) {
            this.verificationImage = imagePath;
        }

        this.verificationMemo = memo;
    }

    public void deleteVerifyUserChallenge() {
        LocalDate now = LocalDate.now();
        LocalDateTime endOfToday = LocalDateTime.of(now, LocalTime.of(23,59,59));

        if (this.challengeDate.toLocalDate().equals(now))
            this.verificationStatus = ChallengeStatus.WAITING;
        else if (this.challengeDate.isAfter(endOfToday))
            throw BizException.withUserMessageKey("exception.user.challenge.verify.delete.must.be.before.today").build();
        else
            this.verificationStatus = ChallengeStatus.FAIL;

        this.verificationImage = null;
        this.verificationMemo = null;
    }

    public void verifyFail() {
        this.verificationStatus = ChallengeStatus.FAIL;
    }

}
