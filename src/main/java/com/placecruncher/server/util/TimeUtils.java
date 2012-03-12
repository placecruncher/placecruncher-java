package com.placecruncher.server.util;

import java.util.Date;

public class TimeUtils {
    private TimeUtils() { }

    public static Date getCurrentTime()
    {
        return new Date(System.currentTimeMillis());
    }
}
