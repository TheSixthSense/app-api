package com.app.api.challenge.controller;

import com.app.api.challenge.dto.*;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "유저의 챌린지")
@RestController
@RequiredArgsConstructor
public class UserChallengeController {

    private final UserChallengeService userChallengeService;

    @ApiOperation(value = "챌린지 인증")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "챌린지 인증 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "챌린지 인증 실패")
    })
    @PostMapping(value = "/user/challenge/verify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse<UserChallengeVerifyResponseDto> registerUserChallenge(@Validated UserChallengeVerifyDto userChallengeVerifyDto,
                                                                              @RequestPart("images") List<MultipartFile> multipartFileList) {
        UserChallengeVerifyResponseDto userChallengeVerifyResponseDto = userChallengeService
                .verifyUserChallenge(userChallengeVerifyDto, multipartFileList);

        return RestResponse
                .withData(userChallengeVerifyResponseDto)
                .withUserMessageKey("success.user.challenge.register")
                .build();
    }

    @ApiOperation(value = "챌린지 통계")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "통계정보 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "통계정보 조회 실패")
    })
    @GetMapping("/user/challenge/stats")
    public RestResponse<UserChallengeStatsDto> getStats(@ApiIgnore @User UserDTO userDTO) {
        UserChallengeStatsDto userChallengeStatsDto = userChallengeService.getUserStats(userDTO);

        return RestResponse
                .withData(userChallengeStatsDto)
                .withUserMessageKey("success.user.challenge.stats")
                .build();
    }

    @ApiOperation(value = "챌린지 참여")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "Map", response = RestResponse.class, message = "유저 챌린지 참여 성공"),
            @ApiResponse(code = 400, responseContainer = "Map", response = RestResponse.class, message = "유저 챌린지 참여 실패")
    })
    @PostMapping("/challenge/join")
    public RestResponse<?> joinChallenge(@Validated @RequestBody UserChallengeJoinDto userChallengeJoinDto, @ApiIgnore @User UserDTO userDTO) {
        userChallengeService.joinChallenge(userChallengeJoinDto, userDTO.getId());
        return RestResponse
                .withUserMessageKey("success.user.challenge.join.success")
                .build();
    }

    @ApiOperation(value = "챌린지 일별 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "유저 챌린지 일별 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "유저 챌린지 일별 조회 실패")
    })
    @GetMapping("/user/challenge/list")
    public RestResponse<List<UserChallengeDayListDto>> getChallengeList(@RequestParam(name = "date") String date, @ApiIgnore @User UserDTO userDTO) {
        List<UserChallengeDayListDto> userChallengeList = userChallengeService.getChallengeListByUserId(date, userDTO.getId());
        return RestResponse
                .withData(userChallengeList)
                .withUserMessageKey("success.user.challenge.list.found")
                .build();
    }

}
