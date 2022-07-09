package com.app.api.core.exception;

/**
 * <pre>
 * Business Error
 * </pre>
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 3584878617244811296L;

    private static final Class<?> clazz = BizException.class;

    public BizException(String systemMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                        String userMessage) {
        super(systemMessage, cause, enableSuppression, writableStackTrace, userMessage);
    }

    public static BaseExceptionBuilder withUserMessage(String userMessage) {
        return new BaseExceptionBuilder(clazz)
                .withUserMessage(userMessage);
    }

    public static BaseExceptionBuilder withUserMessage(String userMessageFormat, Object... objects) {
        return new BaseExceptionBuilder(clazz)
                .withUserMessage(userMessageFormat, objects);
    }

    public static BaseExceptionBuilder withUserMessageKey(String userMessageKey) {
        return new BaseExceptionBuilder(clazz)
                .withUserMessageKey(userMessageKey);
    }

    public static BaseExceptionBuilder withUserMessageKey(String userMessageKeyFormat, Object... objects) {
        return new BaseExceptionBuilder(clazz)
                .withUserMessageKey(userMessageKeyFormat, objects);
    }

    public static BaseExceptionBuilder withSystemMessage(String systemMessage) {
        return new BaseExceptionBuilder(clazz)
                .withSystemMessage(systemMessage);
    }

    public static BaseExceptionBuilder withSystemMessage(String systemMessageFormat, Object... objects) {
        return new BaseExceptionBuilder(clazz)
                .withSystemMessage(systemMessageFormat, objects);
    }
}
