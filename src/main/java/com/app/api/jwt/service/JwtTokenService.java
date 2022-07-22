package com.app.api.jwt.service;

import com.app.api.core.application.JwtProvider;
import com.app.api.core.exception.BizException;
import com.app.api.jwt.dto.CreateTokenRequestDto;
import com.app.api.jwt.dto.CreateTokenResponseDto;
import com.app.api.jwt.dto.RefreshTokenRequestDto;
import com.app.api.jwt.dto.RefreshTokenResponseDto;
import com.app.api.jwt.entity.RefreshToken;
import com.app.api.jwt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public CreateTokenResponseDto createToken(CreateTokenRequestDto requestDto) {

        String newAccessToken = jwtProvider.createAccessToken(requestDto);
        String newRefreshToken = jwtProvider.createRefreshToken(requestDto);
        LocalDateTime expireTime = jwtProvider.getExpireTime(newRefreshToken);

        refreshTokenRepository.findByUserId(requestDto.getUserId())
                .ifPresentOrElse(
                        refreshToken -> refreshToken.updateRefreshToken(newRefreshToken, expireTime),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .userId(requestDto.getUserId())
                                .refreshToken(newRefreshToken)
                                .expiredTime(expireTime)
                                .build())
                );

        return CreateTokenResponseDto.builder()
                .userId(requestDto.getUserId())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto requestDto) throws Exception {
        String refreshToken = requestDto.getRefreshToken();

        // validation Token
        jwtProvider.validateToken(refreshToken);

        // validation Claims
        Claims claims = jwtProvider.parseClaims(refreshToken);
        jwtProvider.validateClaims(claims);

        if (claims.getExpiration().before(new Date())) {
            throw BizException.withUserMessageKey("exception.jwt.token.expire").build();
        }

        Long userId = requestDto.getUserId();

        refreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken)
                .orElseThrow(() -> BizException.withUserMessageKey("exception.jwt.token.invalid").build());

        // 토큰 생성
        String accessToken = jwtProvider.createAccessToken(new CreateTokenRequestDto(userId));

        return RefreshTokenResponseDto.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
