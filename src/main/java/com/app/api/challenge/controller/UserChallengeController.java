package com.app.api.challenge.controller;

import com.app.api.challenge.dto.UserChallengeJoinDto;
import com.app.api.challenge.service.UserChallengeService;
import com.app.api.core.response.RestResponse;
import com.app.api.user.aop.User;
import com.app.api.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "유저의 챌린지")
@RestController
@RequiredArgsConstructor
public class UserChallengeController {

    private final UserChallengeService userChallengeService;

    @ApiOperation(value = "유저 챌린지 참여")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "유저 챌린지 참여 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "유저 챌린지 참여 실패")
    })
    @PostMapping("/challenge/join")
    public RestResponse<?> joinChallenge(@Validated @RequestBody UserChallengeJoinDto userChallengeJoinDto, @ApiIgnore @User UserDTO userDTO) {
        userChallengeService.joinChallenge(userChallengeJoinDto, userDTO.getId());
        return RestResponse
                .withUserMessageKey("success.challenge.join.success")
                .build();
    }
}
