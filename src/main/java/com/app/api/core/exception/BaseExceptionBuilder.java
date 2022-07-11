package com.app.api.core.exception;

import com.app.api.core.config.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * <pre>
 * BaseException 의 빌더클래스.
 * </pre>
 * @see BaseException
 */
public class BaseExceptionBuilder {

    private String userMessage;
    private String userMessageKey;
    private Object[] userMessageArgs;
    private String systemMessage;
    private Throwable cause;
    private boolean enableSuppression = false;
    private boolean writableStackTrace = true;

    private final Class<?> exceptionType;
    private final MessageSource messageSource;

    public BaseExceptionBuilder(Class<?> exceptionType) {
        this.exceptionType = exceptionType;
        this.messageSource = ApplicationContextProvider
                .getApplicationContext()
                .getBean("messageSource", MessageSource.class);
    }

    public BaseExceptionBuilder withUserMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }

    public BaseExceptionBuilder withUserMessage(String userMessageFormat, Object... objects) {
        return withUserMessage(formatMess(userMessageFormat, objects));
    }

    public BaseExceptionBuilder withUserMessageKey(String userMessageKey, Object... objects) {
        this.userMessageKey = userMessageKey;
        this.userMessageArgs = objects;
        return this;
    }

    public BaseExceptionBuilder withUserMessageKey(String userMessageKey) {
        return withUserMessageKey(userMessageKey, (Object[]) null);
    }

    public BaseExceptionBuilder withSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
        return this;
    }

    public BaseExceptionBuilder withSystemMessage(String systemMessageFormat, Object... objects) {
        return withSystemMessage(formatMess(systemMessageFormat, objects));
    }

    public BaseExceptionBuilder withCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public BaseExceptionBuilder withEnableSuppression(boolean enableSuppression) {
        this.enableSuppression = enableSuppression;
        return this;
    }

    public BaseExceptionBuilder withWritableStackTrace(boolean writableStackTrace) {
        this.writableStackTrace = writableStackTrace;
        return this;
    }

    /**
     * 설정한 값들을 이용하여, 해당 타입의 BaseException 객체 생성.
     */
    public BaseException build() {
        // userMessageKey로 userMessage 설정
        if (StringUtils.isNotBlank(this.userMessageKey)) {
            this.userMessage = messageSource.getMessage(this.userMessageKey, this.userMessageArgs,
                                                LocaleContextHolder.getLocale());
        }

        // 메세지 없을 시 예외별 기본메세지 전송
        if (StringUtils.isBlank(this.userMessage)) {
            if (exceptionType.equals(BizException.class)) {
                this.userMessage = messageSource.getMessage(
                                            "exception.common.biz",
                                            null, LocaleContextHolder.getLocale());
            }
        }

        BaseException be = null;
        if (exceptionType.equals(BizException.class)) {
            be = new BizException(this.systemMessage,
                    this.cause,
                    this.enableSuppression,
                    this.writableStackTrace,
                    this.userMessage
                    );
        }

        return be;
    }



    public static String formatMess(String format, Object... objects) {
        return MessageFormatter.arrayFormat(format, objects).getMessage();
    }


}
