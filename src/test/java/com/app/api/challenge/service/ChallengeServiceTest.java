package com.app.api.challenge.service;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.config.BaseTest;
import com.app.api.core.exception.BizException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ChallengeServiceTest extends BaseTest {

    @Mock
    private ChallengeService challengeService;

    ChallengeListDto challengeListDto;

    @Test
    @DisplayName("카테고리별 챌린지 조회 성공")
    void successChallengeListByCategoryId() throws Exception {
        challengeListDto = ChallengeListDto.builder()
                .id(1)
                .categoryId(10)
                .name("두부로 단백질 채우기")
                .build();

        given(challengeService.getChallengeListByCategoryId(anyLong())).willReturn(Arrays.asList(challengeListDto));

        List<ChallengeListDto> challengeListDtoList = challengeService.getChallengeListByCategoryId(anyLong());

        assertAll(
                () -> assertThat(challengeListDtoList).isInstanceOf(List.class),
                () -> assertThat(challengeListDtoList).extracting("id").contains(challengeListDto.getId()),
                () -> assertThat(challengeListDtoList).extracting("categoryId").contains(challengeListDto.getCategoryId()),
                () -> assertThat(challengeListDtoList).extracting("name").contains(challengeListDto.getName()),
                () -> verify(challengeService).getChallengeListByCategoryId(anyLong())
        );
    }

    @Test
    @DisplayName("카테고리별 챌린지 조회 실패")
    void failChallengeListByCategoryId() throws Exception {
        given(challengeService.getChallengeListByCategoryId(anyLong())).willThrow(BizException.withUserMessageKey("exception.challenge.list.not.found").build());

        assertThrows(BizException.class,
                () -> challengeService.getChallengeListByCategoryId(anyLong()),
                messageComponent.getMessage("exception.challenge.list.not.found")
        );
    }
}
