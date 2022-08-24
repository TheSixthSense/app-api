package com.app.api.core.exception;

import com.app.api.core.response.RestResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Map<String, Object>> bizHandle(BizException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error(ex.toStringWithStackTrace());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.returnError());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestResponse<?>> httpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request, HttpServletResponse response) {
        log.error("[HttpRequestMethodNotSupportedException", ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse
                        .withSystemMessageKey("exception.common.http.method.not.exist")
                        .build()
                );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<RestResponse<?>> multipart(MaxUploadSizeExceededException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("[MaxUploadSizeExceededException]", ex);



        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestResponse
                        .withUserMessageKey("exception.multipart.upload.size.exceed", (Object[]) new String[]{maxFileSize, maxRequestSize})
                        .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<?>> httpMessageNotReadableHandle(HttpMessageNotReadableException ex, HttpServletRequest request,
                                               HttpServletResponse response) {
        log.error("[HttpMessageNotReadableException]", ex);

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(RestResponse
                        .withUserMessageKey("exception.validate.need")
                        .build()
                );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponse<?>> validHandle(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("[BindException]", ex);

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, Object> validateError = new HashMap<>();

        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                validateError.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(RestResponse
                        .withData(validateError)
                        .withUserMessageKey("exception.validate.need")
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse<?>> exceptionHandle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("[Exception]", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse
                        .withUserMessageKey("exception.common")
                        .build()
                );
    }
}
