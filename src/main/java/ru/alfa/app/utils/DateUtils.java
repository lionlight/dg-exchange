package ru.alfa.app.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final LocalDate date = LocalDate.now(ZoneOffset.UTC);

    public static String getCurrentDateAsFormattedString() {
        return dateTimeFormatter.format(date);
    }

    public static String getYesterdaysDateAsFormattedString() {
        date.minusDays(1);
        return dateTimeFormatter.format(date);
    }

}
