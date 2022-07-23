package com.app.api.auth.service;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.dto.RenewAuthTokenDTO;
import com.app.api.common.util.Url;
import com.app.api.core.application.JwtProvider;
import com.app.api.core.exception.BizException;
import com.app.api.jwt.dto.TokenDto;
import com.app.api.jwt.entity.RefreshToken;
import com.app.api.jwt.repository.RefreshTokenRepository;
import com.app.api.user.entity.User;
import com.app.api.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final JwtProvider jwtProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 로그인
     */
    @Transactional
    public AuthTokenDTO login(AuthLoginDTO authLoginDTO) throws JsonProcessingException {
        User user = userRepository.findByAppleId(authLoginDTO.getAppleId())
                .orElseThrow(() -> BizException.
                        withUserMessageKey("exception.auth.appleId.not.found")
                        .build());

        Long userId = user.getId();
        TokenDto tokenDto = new TokenDto(userId);

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
    public AuthTokenDTO renewAccessToken(RenewAuthTokenDTO renewAuthTokenDTO) throws JsonProcessingException {
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
