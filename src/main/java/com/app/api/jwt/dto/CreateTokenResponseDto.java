package com.app.api.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateTokenResponseDto {
    private long userId;
    private String accessToken;
    private String refreshToken;
}
