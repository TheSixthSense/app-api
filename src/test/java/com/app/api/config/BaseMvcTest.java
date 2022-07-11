package com.app.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@EnableMockMvc
public class BaseMvcTest extends BaseTest{

    @Autowired
    protected MockMvc mockMvc;

}
