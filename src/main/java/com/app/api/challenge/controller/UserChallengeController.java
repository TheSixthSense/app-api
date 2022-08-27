package com.app.api.challenge.controller;

import com.app.api.challenge.dto.UserChallengeStatsDto;
import com.app.api.challenge.dto.UserChallengeVerifyDeleteDto;
import com.app.api.challenge.dto.UserChallengeVerifyRegDto;
import com.app.api.challenge.dto.UserChallengeVerifyResponseDto;
import com.app.api.challenge.service.UserChallengeService;
import com.app.api.core.response.RestResponse;
import com.app.api.user.aop.User;
import com.app.api.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "사용자 챌린지")
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
    public RestResponse<UserChallengeVerifyResponseDto> registerUserChallenge(
            @Validated UserChallengeVerifyRegDto userChallengeVerifyRegDto,
            @RequestPart("images")List<MultipartFile> multipartFileList) {
        UserChallengeVerifyResponseDto userChallengeVerifyResponseDto = userChallengeService
                .verifyUserChallenge(userChallengeVerifyRegDto, multipartFileList);

        return RestResponse
                .withData(userChallengeVerifyResponseDto)
                .withUserMessageKey("success.user.challenge.verify.register")
                .build();
    }

    @ApiOperation(value = "챌린지 인증내역 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "챌린지 인증내역 삭제 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "챌린지 인증내역 삭제 실패")
    })
    @DeleteMapping(value = "/user/challenge/verify")
    public RestResponse<Object> getUserChallenge(@ApiIgnore @User UserDTO userDTO,
                                                 @Validated UserChallengeVerifyDeleteDto userChallengeVerifyDeleteDto) {
        userChallengeService.deleteUserChallengeVerify(userDTO, userChallengeVerifyDeleteDto);
        return RestResponse
                .withUserMessageKey("success.user.challenge.verify.delete")
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
}
