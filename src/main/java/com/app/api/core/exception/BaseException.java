package com.app.api.core.exception;

import com.app.api.core.response.RestResponseMeta;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Exception 의 기본클래스
 * </pre>
 * @see BizException
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5119762081943226106L;

    private final RestResponseMeta meta = new RestResponseMeta();

    public String toStringWithStackTrace() {
        return String.format("[%s]\n" +
                        "%s\n" +
                        " - userMessage: %s\n" +
                        " - systemMessage: %s\n",
                this.getClass().getSimpleName(),
                ExceptionUtils.getStackTrace(this),
                meta.getUserMessage(),
                meta.getSystemMessage()
        );
    }

    public Map<String, Object> returnError() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", null);
        result.put("meta", meta);

        return result;
    }


    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    | Constructor
    |-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

    public BaseException() {
        super();
    }

    public BaseException(String systemMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                         String userMessage) {
        super(systemMessage, cause, enableSuppression, writableStackTrace);

        meta.setSystemMessage(systemMessage);
        meta.setUserMessage(userMessage);
    }
}