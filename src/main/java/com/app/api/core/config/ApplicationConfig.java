package com.app.api.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class ApplicationConfig {

    /**
     * DispatcherServlet 설정
     * 잘못된 path 요청이 올 경우 @ControllerAdvice 을 통하여 예외처리가 되지 않기 때문에 설정처리
     * 기본적으로 NoHandlerFound Error는 exception을 처리하는 것이 아닌 에러페이지를 보여주는 형태
     */
    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
