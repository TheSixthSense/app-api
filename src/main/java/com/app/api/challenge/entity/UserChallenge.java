package com.app.api.challenge.entity;

import com.app.api.challenge.enums.ChallengeStatus;
import com.app.api.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column
    private String verificationMemo;

    @Column
    private String verificationImage;

}
