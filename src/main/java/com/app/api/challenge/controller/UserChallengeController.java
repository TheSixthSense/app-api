package com.app.api.challenge.controller;

import com.app.api.challenge.dto.UserChallengeVerifyDto;
import com.app.api.challenge.dto.UserChallengeVerifyResponseDto;
import com.app.api.challenge.service.UserChallengeService;
import com.app.api.core.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public RestResponse<UserChallengeVerifyResponseDto> registerUserChallenge(@Validated UserChallengeVerifyDto userChallengeVerifyDto,
                                                      @RequestPart("images")List<MultipartFile> multipartFileList) {
        UserChallengeVerifyResponseDto userChallengeVerifyResponseDto = userChallengeService
                .verifyUserChallenge(userChallengeVerifyDto, multipartFileList);

        return RestResponse
                .withData(userChallengeVerifyResponseDto)
                .withUserMessageKey("success.user.challenge.register")
                .build();
    }
}
