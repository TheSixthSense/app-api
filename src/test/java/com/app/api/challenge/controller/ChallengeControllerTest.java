package com.app.api.challenge.controller;

import com.app.api.challenge.dto.ChallengeListDto;
import com.app.api.challenge.service.ChallengeService;
import com.app.api.config.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ChallengeControllerTest extends BaseTest {

    @MockBean
    ChallengeService challengeService;

    ChallengeListDto challengeListDto;

    @BeforeEach
    void setUp() {
        challengeListDto = ChallengeListDto.builder()
                .id(1)
                .categoryId(10)
                .name("두유 대신 우유 마시기")
                .build();
    }

    @Test
    @DisplayName("카테고리별 챌린지 조회 응답 성공")
    @WithMockUser(roles = "USER")
    void successChallengeList() throws Exception {
        given(challengeService.getChallengeListByCategoryId(anyLong())).willReturn(Arrays.asList(challengeListDto));

        final ResultActions actions = mockMvc.perform(get("/challenge/{categoryId}", anyLong())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value((int) challengeListDto.getId()))
                .andExpect(jsonPath("$..categoryId").value((int) challengeListDto.getCategoryId()))
                .andExpect(jsonPath("$..name").value(challengeListDto.getName()))
                .andExpect(jsonPath("meta.userMessage")
                        .value(messageComponent.getMessage("success.challenge.list.found")));
    }
}
