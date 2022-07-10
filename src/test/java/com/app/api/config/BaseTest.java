package com.app.api.config;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class BaseTest {

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
