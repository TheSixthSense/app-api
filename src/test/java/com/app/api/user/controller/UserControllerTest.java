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
    protected String clientSecret = "eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw";
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