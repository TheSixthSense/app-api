package com.app.api.jwt.entity;

import com.app.api.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredTime;

    @Builder
    public RefreshToken(Long id, Long userId, String refreshToken, LocalDateTime expiredTime) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime expiredTime) {
        this.refreshToken = refreshToken;
        this.expiredTime = expiredTime;
    }
}
