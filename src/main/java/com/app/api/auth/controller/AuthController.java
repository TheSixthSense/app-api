
package com.app.api.auth.controller;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.dto.RenewAuthTokenDTO;
import com.app.api.auth.service.AuthService;
import com.app.api.core.response.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "로그인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "로그인 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "로그인 실패")
    })
    @PostMapping("/auth/login")
    public RestResponse<AuthTokenDTO> login(@Validated @RequestBody AuthLoginDTO authLoginDTO) {
        AuthTokenDTO authTokenDTO = authService.login(authLoginDTO);

        return RestResponse
                .withData(authTokenDTO)
                .withUserMessageKey("success.auth.login")
                .build();
    }

    @ApiOperation(value = "토큰 재발급")
    @ApiResponses(value = {
        @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "발급성공"),
        @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "발급실패")
    })
    @PostMapping("/auth/refreshToken")
    public RestResponse<AuthTokenDTO> getNewAccessToken(@Validated @RequestBody RenewAuthTokenDTO renewAuthTokenDTO) {
        AuthTokenDTO authTokenDTO = authService.renewAccessToken(renewAuthTokenDTO);

        return RestResponse
                .withData(authTokenDTO)
                .withUserMessageKey("success.auth.renew.accessToken")
                .build();
    }
}
