package com.peakoapp.core.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * The {@code DateUtils} class implements date related utility methods.
 *
 * @version 0.1.0
 */
public final class DateUtils {
    /**
     * Adds the given days to the given date.
     *
     * @param date The start date.
     * @param days The days to add.
     * @return A new date after calculations.
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
