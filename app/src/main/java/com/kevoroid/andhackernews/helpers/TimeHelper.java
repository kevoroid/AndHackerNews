package com.kevoroid.andhackernews.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kevin on 5/27/17.
 */

public class TimeHelper {

    public static long getCurrentEpochTime() {
        return System.currentTimeMillis();
    }

    public static long getUnixEpochTime() {
        return getCurrentEpochTime() / 1000L;
    }

    public static boolean isTimePassed(long startTime, long thresholdMills) {
        return getCurrentEpochTime() - startTime > thresholdMills;
    }

    public static String returnActualDate(String epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        long actualDate = Long.parseLong(epoch) * 1000L;
        return sdf.format(new Date(actualDate));
    }
}
