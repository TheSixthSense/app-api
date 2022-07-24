package com.app.api.auth.service;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.dto.RenewAuthTokenDTO;
import com.app.api.core.application.JwtProvider;
import com.app.api.core.exception.BizException;
import com.app.api.jwt.dto.TokenDto;
import com.app.api.jwt.entity.RefreshToken;
import com.app.api.jwt.repository.RefreshTokenRepository;
import com.app.api.user.entity.User;
import com.app.api.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

import static com.app.api.common.util.form.Form.checkEmailForm;
import static com.app.api.common.util.jwt.JwtUtil.getEmailFromAppleSecretToken;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 로그인
     */
    @Transactional
    public AuthTokenDTO login(AuthLoginDTO authLoginDTO) {
        String email = getEmailFromAppleSecretToken(authLoginDTO.getClientSecret());

        // 이메일 형식 체크
        checkEmailForm(email);

        User user = userRepository.findByAppleIdAndEmail(authLoginDTO.getAppleId(), email)
                .orElseThrow(() -> BizException.
                        withUserMessageKey("exception.auth.appleId.not.found")
                        .build());

        Long userId = user.getId();
        TokenDto tokenDto = new TokenDto(userId, user.getUserRoleType());

        String accessToken = jwtProvider.createAccessToken(tokenDto);
        String refreshToken = jwtProvider.createRefreshToken(tokenDto);
        LocalDateTime expireTime = jwtProvider.getExpireTime(refreshToken);

        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        tokenInfo -> tokenInfo.updateRefreshToken(refreshToken, expireTime),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .userId(userId)
                                .refreshToken(refreshToken)
                                .expiredTime(expireTime)
                                .build())
                );

        return AuthTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * refreshToken 정보를 기반으로 accessToken 재생성
     */
    public AuthTokenDTO renewAccessToken(RenewAuthTokenDTO renewAuthTokenDTO) {
        User user = userRepository.findByAppleId(renewAuthTokenDTO.getAppleId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.not.found").build());

        Long userId = user.getId();
        String refreshToken = renewAuthTokenDTO.getRefreshToken();

        // validation Token
        jwtProvider.validateToken(refreshToken);

        // validation Claims
        Claims claims = jwtProvider.parseClaims(refreshToken);
        jwtProvider.validateClaims(claims);

        if (claims.getExpiration().before(new Date())) {
            throw BizException.withUserMessageKey("exception.jwt.token.expire").build();
        }

        refreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken)
                .orElseThrow(() -> BizException.withUserMessageKey("exception.jwt.token.refresh.invalid").build());

        // 토큰 생성
        String accessToken = jwtProvider.createAccessToken(new TokenDto(userId));

        return AuthTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
