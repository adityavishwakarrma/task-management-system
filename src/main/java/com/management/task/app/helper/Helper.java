package com.management.task.app.helper;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Helper {

    public static Date parseDate(LocalDateTime localDateTime) throws ParseException {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date from = Date.from(instant);
        return from;
    }
}

