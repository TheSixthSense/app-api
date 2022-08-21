package com.app.api.config;

import com.app.api.auth.dto.AuthLoginDTO;
import com.app.api.auth.dto.AuthTokenDTO;
import com.app.api.auth.service.AuthService;
import com.app.api.common.MessageComponent;
import com.app.api.user.entity.User;
import com.app.api.user.enums.Gender;
import com.app.api.user.enums.UserRoleType;
import com.app.api.user.enums.VegannerStage;
import com.app.api.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected MessageComponent messageComponent;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    protected static String appleId = "001805.7d48278a5f8d4c618263bef5a616f7dc.1512_test_static";
    protected static String clientSecret = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RfY29kZUBnYW1pbC5jb20iLCJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.WJEl1X9KMLhBgn5RTC5VGeYMZHMUjVQc6QkFddM-Sqs";
    protected static String email = "test_code@gamil.com";
    protected static String nickName = "비건첼린져_TestModule";
    protected static Gender gender = Gender.MALE;
    protected static UserRoleType userRoleType = UserRoleType.USER;
    protected static String birthDay = "19920910";
    protected static VegannerStage vegannerStage = VegannerStage.BEGINNER;

    protected static String accessToken;

    @BeforeEach
    void setup() {
        userRepository.findByAppleId(appleId)
                .orElseGet(() -> userRepository.save(
                                User.builder()
                                        .appleId(appleId)
                                        .email(email)
                                        .nickName(nickName)
                                        .gender(gender)
                                        .userRoleType(userRoleType)
                                        .birthDay(birthDay)
                                        .vegannerStage(vegannerStage)
                                        .build()
                        )
                );
        AuthLoginDTO authLoginDTO = AuthLoginDTO.builder()
                .appleId(appleId)
                .clientSecret(clientSecret)
                .build();
        AuthTokenDTO login = authService.login(authLoginDTO);
        accessToken = login.getAccessToken();
    }

    @AfterEach
    void clean() {

    }

    @BeforeAll
    static void startTest() {

    }

    @AfterAll
    static void endTest() {

    }
}
