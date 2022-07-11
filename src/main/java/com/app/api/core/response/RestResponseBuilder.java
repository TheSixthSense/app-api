package com.app.api.core.response;

import com.app.api.core.config.ApplicationContextProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class RestResponseBuilder<T> {

    private String userMessage;
    private String userMessageKey;
    private Object[] userMessageArgs;
    private String systemMessage;
    private String systemMessageKey;
    private Object[] systemMessageArgs;
    private T data;

    private MessageSource messageSource;

    public RestResponseBuilder() {
        this.messageSource = ApplicationContextProvider
                .getApplicationContext()
                .getBean("messageSource", MessageSource.class);
    }

    public RestResponseBuilder<T> withData(T data) {
        this.data = data;
        return this;
    }

    public RestResponseBuilder<T> withUserMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }

    public RestResponseBuilder<T> withUserMessageKey(String userMessageKey) {
        return withUserMessageKey(userMessageKey, (Object) null);
    }

    public RestResponseBuilder<T> withUserMessageKey(String userMessageKey, Object... objects) {
        this.userMessageKey = userMessageKey;
        this.userMessageArgs = objects;
        return this;
    }

    public RestResponseBuilder<T> withSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
        return this;
    }

    public RestResponseBuilder<T> withSystemMessageKey(String systemMessageKey) {
        return withSystemMessageKey(systemMessageKey, (Object) null);
    }

    public RestResponseBuilder<T> withSystemMessageKey(String systemMessageKey, Object... objects) {
        this.systemMessageKey = systemMessageKey;
        this.systemMessageArgs = objects;
        return this;
    }

    public RestResponse<T> build() {
        RestResponse<T> restResponse = new RestResponse<>(this.data);

        if (StringUtils.isNotBlank(this.userMessage)) {
            restResponse.setMetaUserMessage(this.userMessage);
        }

        if (StringUtils.isNotBlank(this.systemMessage)) {
            restResponse.setMetaSystemMessage(this.systemMessage);
        }

        if (StringUtils.isNotBlank(this.userMessageKey)) {
            restResponse.setMetaUserMessage(messageSource.getMessage(this.userMessageKey, this.userMessageArgs,
                                    LocaleContextHolder.getLocale()));
        }

        if (StringUtils.isNotBlank(this.systemMessageKey)) {
            restResponse.setMetaSystemMessage(messageSource.getMessage(this.systemMessageKey, this.systemMessageArgs,
                    LocaleContextHolder.getLocale()));
        }

        return restResponse;
    }

    private String formatMess(String format, Object... objects) {
        return MessageFormatter.arrayFormat(format, objects).getMessage();
    }
}
