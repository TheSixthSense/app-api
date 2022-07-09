package com.app.api.core.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = -7857710275656843711L;

    @Getter
    @Setter
    private T data;

    @Getter
    private final RestResponseMeta meta = new RestResponseMeta();

    public RestResponse(T data) {
        this.data = data;
    }

    public static <T> RestResponseBuilder<T> withUserMessage(String userMessage) {
        return new RestResponseBuilder<T>().withUserMessage(userMessage);
    }

    public static <T> RestResponseBuilder<T> withUserMessageKey(String userMessageKey) {
        return new RestResponseBuilder<T>().withUserMessageKey(userMessageKey);
    }

    public static <T> RestResponseBuilder<T> withSystemMessage(String systemMessage) {
        return new RestResponseBuilder<T>().withSystemMessage(systemMessage);
    }

    public static <T> RestResponseBuilder<T> withSystemMessageKey(String systemMessageKey) {
        return new RestResponseBuilder<T>().withSystemMessageKey(systemMessageKey);
    }

    public static <T> RestResponseBuilder<T> withData(T data) {
        return new RestResponseBuilder<T>().withData(data);
    }

    public void setMetaUserMessage(String userMessage) { this.meta.setUserMessage(userMessage); }

    public void setMetaSystemMessage(String systemMessage) { this.meta.setSystemMessage(systemMessage); }
}
