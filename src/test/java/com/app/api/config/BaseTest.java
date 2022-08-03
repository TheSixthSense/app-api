package com.app.api.config;

import com.app.api.common.MessageComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @BeforeEach
    void setup() {
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
