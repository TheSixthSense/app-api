package com.app.api.jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class RefreshTokenRequestDto {
    @NotNull
    private long userId;
    @NotBlank
    private String refreshToken;
}
