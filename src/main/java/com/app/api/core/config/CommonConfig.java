package com.app.api.core.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
public class CommonConfig {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.KOREA);

        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasenames("messages/messages");
        bundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return bundleMessageSource;
    }
}
