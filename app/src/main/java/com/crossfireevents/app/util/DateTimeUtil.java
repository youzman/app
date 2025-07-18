package com.crossfireevents.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for date and time operations.
 */
public class DateTimeUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    
    /**
     * Formats a timestamp to a date string.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return The formatted date string.
     */
    public static String formatDate(long timestamp) {
        return DATE_FORMAT.format(new Date(timestamp));
    }
    
    /**
     * Formats a timestamp to a time string.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return The formatted time string.
     */
    public static String formatTime(long timestamp) {
        return TIME_FORMAT.format(new Date(timestamp));
    }
    
    /**
     * Formats a timestamp to a date and time string.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return The formatted date and time string.
     */
    public static String formatDateTime(long timestamp) {
        return DATE_TIME_FORMAT.format(new Date(timestamp));
    }
    
    /**
     * Checks if a timestamp is in the future.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return True if the timestamp is in the future, false otherwise.
     */
    public static boolean isFuture(long timestamp) {
        return timestamp > System.currentTimeMillis();
    }
    
    /**
     * Checks if a timestamp is in the past.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return True if the timestamp is in the past, false otherwise.
     */
    public static boolean isPast(long timestamp) {
        return timestamp < System.currentTimeMillis();
    }
    
    /**
     * Checks if a timestamp is today.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return True if the timestamp is today, false otherwise.
     */
    public static boolean isToday(long timestamp) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(timestamp);
        calendar2.setTimeInMillis(System.currentTimeMillis());
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
               calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * Gets a relative time string for a timestamp.
     * For example, "اليوم"، "أمس"، "غداً"، or the formatted date.
     *
     * @param timestamp The timestamp in milliseconds.
     * @return The relative time string.
     */
    public static String getRelativeTimeString(long timestamp) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(timestamp);
        calendar2.setTimeInMillis(System.currentTimeMillis());
        
        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) {
                return "اليوم";
            } else if (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) - 1) {
                return "أمس";
            } else if (calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) + 1) {
                return "غداً";
            }
        }
        
        return formatDate(timestamp);
    }
}