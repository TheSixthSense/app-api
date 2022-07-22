package com.app.api.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class RefreshTokenResponseDto {
    @NotNull
    private long userId;
    private String accessToken;
    private String refreshToken;
}
