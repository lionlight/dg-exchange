package ru.alfa.app.utils;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate date = LocalDate.now(ZoneOffset.UTC);

    public String getNowDateAsString() {
        return dateTimeFormatter.format(date);
    }

    public String getYesterdayAsString() {
        date.minusDays(1);
        return dateTimeFormatter.format(date);
    }
}
