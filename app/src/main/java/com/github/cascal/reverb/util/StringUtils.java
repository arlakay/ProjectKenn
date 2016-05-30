package com.github.cascal.reverb.util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    public static String intToStringWithCommas(int n) {
        return NumberFormat.getInstance(Locale.US).format(n);
    }

    public static String getTimestamp(int millis) {

        int hours = millis / 3600000;
        int minutes = (millis % 3600000) / 60000;
        int seconds = ((millis % 3600000) % 60000) / 1000;

        StringBuilder timestamp = new StringBuilder(8);

        if (hours > 0) {
            timestamp.append((hours < 10 ? "0" : ""));
            timestamp.append(hours);
            timestamp.append(":");
        }
        timestamp.append((minutes < 10 ? "0" : ""));
        timestamp.append(minutes);
        timestamp.append(":");
        timestamp.append((seconds < 10 ? "0" : ""));
        timestamp.append(seconds);

        return timestamp.toString();
    }
}
