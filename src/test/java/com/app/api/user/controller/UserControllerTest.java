package com.app.api.user.controller;

import com.app.api.config.BaseTest;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.VegannerStage;
import com.app.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("User")
class UserControllerTest extends BaseTest {

    @Autowired
    protected UserRepository userRepository;

    @Test
    @Transactional
    @Order(1)
    @DisplayName("회원가입")
    public void createUser() throws Exception {
        // given
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("appleId", "001805.7d48278a5f8d4c618263bef5a616f7dc.1512_reg");
        requestData.put("clientSecret", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RfY29kZV9yZWdAZ21haWwuY29tIiwic3ViIjoiMTIzNDU2Nzg5MCIsIm5hbWUiOiJKb2huIERvZSIsImlhdCI6MTUxNjIzOTAyMn0.G2uUPEdQcr_9SMqMNL9fW9y2Zv-a1cwlu80XeHnKBVI");
        requestData.put("nickName", "비건첼리져_회원가입_test");
        requestData.put("gender", Gender.MALE);
        requestData.put("birthDay", "19920910");
        requestData.put("vegannerStage", VegannerStage.BEGINNER);

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
        String nickName = "notDuplicatedNickName";

        // when && then
        mockMvc.perform(get("/check/nick-name")
                        .param("nickName", nickName)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("meta.userMessage")
                        .value(messageComponent.getMessage("success.user.nickname.unique")));
    }

    @Test
    @Transactional
    @Order(3)
    @DisplayName("회원탈퇴")
    public void userWithdraw() throws Exception {
        //given (accessToken)
        //when & then
        mockMvc.perform(delete("/user/withdraw")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("meta.userMessage")
                        .value(messageComponent.getMessage("success.user.withdraw")));

    }
}