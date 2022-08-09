package com.app.api.challenge.controller;

import com.app.api.challenge.entity.Challenge;
import com.app.api.challenge.service.ChallengeService;
import com.app.api.core.response.RestResponse;
import com.app.api.user.dto.UserRegDTO;
import com.app.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "챌린지")
@RestController
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @ApiOperation(value = "카테고리별 챌린지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "챌린지 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "챌린지 조회 실패")
    })
    @PostMapping("/challenge/{categoryId}")
    public RestResponse<List<Challenge>> getChallengeListByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<Challenge> challengeList = challengeService.getChallengeListByCategoryId(categoryId);
        return RestResponse
                .withData(challengeList)
                .withUserMessageKey("success.challenge.list.found")
                .build();
    }

}
