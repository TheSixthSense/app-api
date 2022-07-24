package com.app.api.common.util.form;

import com.app.api.core.exception.BizException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form {

    private static final Pattern EMAIL = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"); // effective java item 6 technique

    /**
     * 이메일 형식 체크
     */
    public static void checkEmailForm(String email) {
        Matcher m = EMAIL.matcher(email);
        if (!m.matches()) {
            throw BizException.withUserMessageKey("exception.common.email.form").build();
        }
    }
}
