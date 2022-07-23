package com.app.api.auth.service;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.dto.RenewAuthTokenDTO;
import com.app.api.common.util.Url;
import com.app.api.core.application.JwtProvider;
import com.app.api.core.exception.BizException;
import com.app.api.jwt.dto.TokenDto;
import com.app.api.user.entity.User;
import com.app.api.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final JwtProvider jwtProvider;

    /**
     * 로그인
     */
    public AuthTokenDTO login(AuthLoginDTO authLoginDTO) throws JsonProcessingException {
        User user = userRepository.findByAppleId(authLoginDTO.getAppleId())
                .orElseThrow(() -> BizException.
                        withUserMessageKey("exception.auth.appleId.not.found")
                        .build());

        TokenDto tokenDto = TokenDto.builder()
                .userId(user.getId())
                .build();

        String accessToken = jwtProvider.createAccessToken(tokenDto);
        String refreshToken = jwtProvider.createRefreshToken(tokenDto);

        return AuthTokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * refreshToken 정보를 기반으로 accessToken 재생성
     */
    public AuthTokenDTO renewAccessToken(RenewAuthTokenDTO renewAuthTokenDTO) throws JsonProcessingException {
        User user = userRepository.findByAppleId(renewAuthTokenDTO.getAppleId())
                .orElseThrow(() -> BizException.withUserMessageKey("exception.user.not.found").build());

        // url
        String securityUrl = UriComponentsBuilder
                .fromHttpUrl(Url.appSecurity + "/auth/refresh")
                .build()
                .toUriString();

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request Body
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("userId", user.getId());
        requestData.put("refreshToken", renewAuthTokenDTO.getRefreshToken());
        String body = objectMapper.writeValueAsString(requestData);

        // request
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> resp = restTemplate.exchange(securityUrl, HttpMethod.POST, request, String.class);

        // convert data
        return objectMapper.readValue(resp.getBody(), AuthTokenDTO.class);
    }
}
