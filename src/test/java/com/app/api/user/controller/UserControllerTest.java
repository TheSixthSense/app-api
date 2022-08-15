package com.app.api.user.controller;

import com.app.api.config.BaseTest;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.VegannerStage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("User")
class UserControllerTest extends BaseTest {
    protected String appleId = "001805.7d48278a5f8d4c618263bef5a616f7dc.1512";
    protected String clientSecret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RNb2R1bGUxMjNAZ21haWwuY29tIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.TzUYx4lm5r1vpft69lbr4jTlQF1tsarxKmqb7cJRYV8";
    protected String nickName = "비건첼린져_TestModule";
    protected Gender gender = Gender.MALE;
    protected String birthDay = "19920910";
    protected VegannerStage vegannerStage = VegannerStage.BEGINNER;

    @Test
    @Transactional
    @Order(1)
    @DisplayName("회원가입")
    public void createUser() throws Exception {
        // given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("appleId", this.appleId);
        requestData.put("clientSecret", this.clientSecret);
        requestData.put("nickName", this.nickName);
        requestData.put("gender", this.gender);
        requestData.put("birthDay", this.birthDay);
        requestData.put("vegannerStage", this.vegannerStage);

        String requestBody = objectMapper.writeValueAsString(requestData);

        // when && then
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("meta.userMessage")
                        .value(messageComponent.getMessage("success.user.create")));
    }

    @Test
    @Transactional
    @Order(2)
    @DisplayName("닉네임 중복확인")
    public void checkDuplicateNickName() throws Exception {
        // given
        String nickName = this.nickName;

        // when && then
        mockMvc.perform(get("/check/nick-name")
                        .param("nickName", nickName)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("meta.userMessage")
                        .value(messageComponent.getMessage("success.user.nickname.unique")));
    }
}