package com.app.api.user.controller;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.service.AuthService;
import com.app.api.core.response.RestResponse;
import com.app.api.user.aop.User;
import com.app.api.user.dto.UserDTO;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.dto.UserUpdateDTO;
import com.app.api.user.dto.UserViewDTO;
import com.app.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "사용자")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "회원가입 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "회원가입 실패")
    })
    @PostMapping("/signup")
    public RestResponse<AuthTokenDTO> signup(@Validated @RequestBody UserRegDTO userRegDTO) {
        // 회원가입
        userService.signup(userRegDTO);

        AuthLoginDTO authLoginDTO = AuthLoginDTO.builder()
                .appleId(userRegDTO.getAppleId())
                .clientSecret(userRegDTO.getClientSecret())
                .build();

        // 로그인 처리
        AuthTokenDTO authTokenDTO = authService.login(authLoginDTO);
        return RestResponse
                .withData(authTokenDTO)
                .withUserMessageKey("success.user.create")
                .build();
    }

    @ApiOperation(value = "닉네임 중복확인")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "동일한 닉네임이 없습니다."),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "해당 닉네임은 이미 존재합니다. 다른 닉네임을 사용하여 주세요.")
    })
    @GetMapping("/check/nick-name")
    public RestResponse<Object> checkDuplicateNickName(@RequestParam(name = "nickName") String nickName) {
        userService.checkDuplicateNickname(nickName);
        return RestResponse
                .withUserMessageKey("success.user.nickname.unique")
                .build();
    }

    @ApiOperation(value = "회원탈퇴")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "회원탈퇴 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "회원탈퇴 실패")
    })
    @DeleteMapping("/user/withdraw")
    public RestResponse<Object> userWithdraw(@ApiIgnore @User UserDTO userDTO) {
        userService.userWithDraw(userDTO);
        return RestResponse
                .withUserMessageKey("success.user.withdraw")
                .build();
    }

    @ApiOperation(value = "회원정보")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "회원정보 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "회원정보 조회 실패")
    })
    @GetMapping("/user/info")
    public RestResponse<UserViewDTO> getUserInfo(@ApiIgnore @User UserDTO userDTO) {
        UserViewDTO userViewDTO = userService.convert(userDTO);
        return RestResponse
                .withData(userViewDTO)
                .withUserMessageKey("success.user.get.info")
                .build();
    }

    @ApiOperation(value = "회원정보 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "회원정보 수정 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "회원정보 수정 실패")
    })
    @PatchMapping("/user/info")
    public RestResponse<UserViewDTO> updateUserInfo(@ApiIgnore @User UserDTO userDTO,
                                                    @Validated @RequestBody UserUpdateDTO userUpdateDTO) {
        UserViewDTO userViewDTO = userService.updateUserInfo(userDTO, userUpdateDTO);
        return RestResponse
                .withData(userViewDTO)
                .withUserMessageKey("success.user.update.info")
                .build();
    }
}
