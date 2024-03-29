package com.app.api.challenge.controller;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.service.ChallengeService;
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

@Api(tags = "챌린지")
@RestController
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @ApiOperation(value = "카테고리별 챌린지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "카테고리별 챌린지 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "카테고리별 챌린지 조회 실패")
    })
    @GetMapping("/challenge/{categoryId}")
    public RestResponse<List<ChallengeListDto>> getChallengeListByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<ChallengeListDto> challengeList = challengeService.getChallengeListByCategoryId(categoryId);
        return RestResponse
                .withData(challengeList)
                .withUserMessageKey("success.challenge.list.found")
                .build();
    }

    @ApiOperation(value = "챌린지 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, responseContainer = "List", response = RestResponse.class, message = "챌린지 목록 조회 성공"),
            @ApiResponse(code = 400, responseContainer = "List", response = RestResponse.class, message = "챌린지 목록 조회 실패")
    })
    @GetMapping("/challenge/list")
    public RestResponse<List<ChallengeListDto>> getChallengeList() {
        List<ChallengeListDto> challengeList = challengeService.getChallengeList();
        return RestResponse
                .withData(challengeList)
                .withUserMessageKey("success.challenge.total.list.found")
                .build();
    }
}
