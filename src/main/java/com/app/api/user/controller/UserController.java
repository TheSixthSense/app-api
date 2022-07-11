package com.app.api.user.controller;

import com.app.api.core.response.RestResponse;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

//    @ApiOperation(value = "내 정보 보기")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "내 정보 보기 성공"),
//            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "내 정보 보기 실패")
//    })
//    @GetMapping("/user/me")
//    public RestResponse<UserDTO> getUserInfo(@ApiIgnore @User UserDTO userDTO) {
//        return RestResponse
//                .withData(userDTO)
//                .withUserMessageKey("success.user.get")
//                .build();
//    }
}
