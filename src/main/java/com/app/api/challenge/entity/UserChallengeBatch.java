package com.app.api.challenge.entity;

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
@Table(name = "user_challenge_batch")
public class UserChallengeBatch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userChallengeId;

    @Column
    private LocalDateTime executedDate;

    public void executeBatch() {
        this.executedDate = LocalDateTime.now();
    }
}
