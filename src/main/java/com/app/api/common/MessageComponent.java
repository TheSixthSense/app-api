package com.app.api.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("messageComponent")
@Slf4j
public class MessageComponent {

    /** 메시지 소스 */
    @Autowired
    private MessageSource messageSource;

    /**
     * 메시지 조회
     * @param code message key
     */
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, null, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 조회
     * @param code message key
     * @param args 메세지의 {0}, {1}, {2} 부분을 대체할 객체들에 대한 array
     */
    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, null, LocaleContextHolder.getLocale());
    }

    /**
     * 메시지 조회
     * @param code message key
     * @param args 메세지의 {0}, {1}, {2} 부분을 대체할 객체들에 대한 array
     * @param defaultMessage 메세지 기본값
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }
}
