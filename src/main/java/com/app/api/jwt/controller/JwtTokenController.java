package com.app.api.jwt.controller;

import com.app.api.core.response.RestResponse;
import com.app.api.jwt.dto.CreateTokenRequestDto;
import com.app.api.jwt.dto.CreateTokenResponseDto;
import com.app.api.jwt.dto.RefreshTokenRequestDto;
import com.app.api.jwt.dto.RefreshTokenResponseDto;
import com.app.api.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class JwtTokenController {

    private final JwtTokenService jwtTokenService;

    /**
     * JWT token create
     */
    @PostMapping(value = "/auth")
    public RestResponse<CreateTokenResponseDto> createToken(@Valid @RequestBody CreateTokenRequestDto createTokenRequestDto) {
        CreateTokenResponseDto responseDto = jwtTokenService.createToken(createTokenRequestDto);
        return RestResponse
                .withData(responseDto)
                .withUserMessageKey("success.jwt.token.create")
                .build();
    }

    /**
     * JWT access token refresh
     */
    @PostMapping(value = "/auth/refresh")
    public RestResponse<RefreshTokenResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) throws Exception {
        RefreshTokenResponseDto responseDto = jwtTokenService.refreshToken(refreshTokenRequestDto);
        return RestResponse
                .withData(responseDto)
                .withUserMessageKey("success.jwt.token.create")
                .build();
    }

}
