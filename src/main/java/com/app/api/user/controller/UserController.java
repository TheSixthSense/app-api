package com.app.api.user.controller;

import com.app.api.core.response.RestResponse;
import com.app.api.user.aop.User;
import com.app.api.user.dto.UserDTO;
import com.app.api.user.dto.UserRegDTO;
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

    @ApiOperation(value = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "회원가입 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "회원가입 실패")
    })
    @PostMapping("/signup")
    public RestResponse<Object> signup(@Validated @RequestBody UserRegDTO userRegDTO) {
        userService.signup(userRegDTO);
        return RestResponse
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

//    @ApiOperation(value = "내 정보 보기")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "내 정보 보기 성공"),
//            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "내 정보 보기 실패")
//    })
//    @GetMapping("/user/me")
//    public RestResponse<UserViewDTO> getUserInfo(@ApiIgnore @User UserDTO userDTO) {
//        UserViewDTO userViewDTO = UserViewDTO.from(userDTO);
//        return RestResponse
//                .withData(userViewDTO)
//                .withUserMessageKey("success.user.get.info")
//                .build();
//    }
}
