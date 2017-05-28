package com.kevoroid.andhackernews.helpers;

/**
 * Created by kevin on 5/27/17.
 */

public class TimeHelper {

    public static long getCurrentEpochTime() {
        return System.currentTimeMillis();
    }

    public static int getUnixTimestamp() {
        return (int) (getCurrentEpochTime() / 1000L);
    }

    public static boolean isTimePassed(long startTime, long thresholdMills) {
        return getCurrentEpochTime() - startTime > thresholdMills;
    }
}
