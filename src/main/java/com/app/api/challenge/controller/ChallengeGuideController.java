package com.app.api.challenge.controller;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.service.ChallengeGuideService;
import com.app.api.core.response.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "챌린지 가이드")
@RestController
@RequiredArgsConstructor
public class ChallengeGuideController {

    private final ChallengeGuideService challengeGuideService;

    @ApiOperation(value = "챌린지별 가이드 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "챌린지별 가이드 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "챌린지별 가이드 조회 실패")
    })
    @GetMapping("/challenge/guide/{challengeId}")
    public RestResponse<List<ChallengeListDto>> getChallengeGuideByChallengeId(@PathVariable("challengeId") Long challengeId) {
        List<ChallengeListDto> guideList = challengeGuideService.getChallengeGuideByChallengeId(challengeId);
        return RestResponse
                .withData(guideList)
                .withUserMessageKey("success.challenge.list.found")
                .build();
    }

}
