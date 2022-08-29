package com.app.api.common.util;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    /**
     * Date -> LocalDateTime
     */
    public static LocalDateTime changeLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Seoul"));
    }

    /**
     * String(yyyy-MM-dd) -> LocalDate
     */
    public static LocalDate changeStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    /**
     * String(yyyy-MM-dd) -> LocalDateTime
     */
    public static LocalDateTime changeStringToLocalDateTime(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.atStartOfDay();
    }

    /**
     * String(yyyy-MM-dd HH:mm:ss) -> LocalDateTime
     */
    public static LocalDateTime changeFullStringToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime;
    }
}
