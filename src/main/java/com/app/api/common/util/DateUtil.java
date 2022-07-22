package com.app.api.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    /**
     * Date -> LocalDateTime
    */
    public static LocalDateTime changeLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Seoul"));
    }
}
