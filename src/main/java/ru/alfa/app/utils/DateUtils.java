package ru.alfa.app.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getCurrentDateAsFormattedString() {
        LocalDate date = LocalDate.now(ZoneOffset.UTC);
        return dateTimeFormatter.format(date);
    }

    public static String getYesterdaysDateAsFormattedString() {
        LocalDate date = LocalDate.now(ZoneOffset.UTC);
        var dateResult = date.minusDays(1);
        return dateTimeFormatter.format(dateResult);
    }

}
