/*
 * $Id: DateUtils.java,v 1.4 2005/10/10 18:02:45 rbair Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.sola.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Scott Violet
 * @version $Revision: 1.4 $
 */
public class DateUtility {

    /**
     * Compares if two dates are equal up to second resolution. Milliseconds are
     * not considered as these can be stripped if the date value is serialized
     * through a web service, etc. If one of the dates is null, false is
     * returned. If both dates are null, true is returned.
     *
     * @param date1
     * @param date2
     * @return true if both dates are equal to the second or both dates are
     * null.
     */
    public static boolean areEqual(Date date1, Date date2) {
        boolean result = false;
        if (date1 == null && date2 == null) {
            // Both dates are null
            result = true;
        } else if (date1 == null || date2 == null) {
            // One of the dates is not null
            result = false;
        } else {
            // Consider dates up to second resolution. 
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            result = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                    && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
                    && cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
                    && cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND);
        }
        return result;

    }

    /**
     * Returns the last millisecond of the specified date.
     *
     * @param date Date to calculate end of day from
     * @return Last millisecond of <code>date</code>
     */
    public static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MINUTE, 59);
            return calendar.getTime();
        }
    }

    /**
     * Returns a new Date with the hours, milliseconds, seconds and minutes set
     * to 0.
     *
     * @param date Date used in calculating start of day
     * @return Start of <code>date</code>
     */
    public static Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            return calendar.getTime();
        }
    }

    /**
     * Returns day in millis with the hours, milliseconds, seconds and minutes
     * set to 0.
     *
     * @param date long used in calculating start of day
     * @return Start of <code>date</code>
     */
    public static long startOfDayInMillis(long date) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the last millisecond of the specified date.
     *
     * @param date long to calculate end of day from
     * @return Last millisecond of <code>date</code>
     */
    public static long endOfDayInMillis(long date) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MILLISECOND, 999);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MINUTE, 59);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the day after
     * <code>date</code>.
     *
     * @param date Date used in calculating next day
     * @return Day after <code>date</code>.
     */
    public static Date nextDay(Date date) {
        return new Date(addDays(date.getTime(), 1));
    }

    /**
     * Adds
     * <code>amount</code> days to
     * <code>time</code> and returns the resulting time.
     *
     * @param time Base time
     * @param amount Amount of increment.
     *
     * @return the <var>time</var> + <var>amount</var> days
     */
    public static long addDays(long time, int amount) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.DAY_OF_MONTH, amount);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Adds the specified number of days to todays date. If endOfDay is true,
     * the date returned is the end of the calculated date
     *
     * @param amount Number of days to add.
     * @param endOfDay flag to indicate the end of the day
     * @return the new date
     */
    public static Date addDays(int amount, boolean endOfDay) {
        Calendar cal = Calendar.getInstance();
        return addDays(cal.getTime(), amount, endOfDay);
    }

    /**
     * Adds the specified number of days to the stated. If endOfDay is true, the
     * date returned is the end of the calculated date
     *
     * @param The date to add the specified number of days to.
     * @param amount Number of days to add.
     * @param endOfDay flag to indicate the end of the day
     * @return the new date
     */
    public static Date addDays(Date baseDate, int amount, boolean endOfDay) {
        Calendar cal = Calendar.getInstance();
        synchronized (cal) {
            cal.setTime(baseDate);
            cal.add(Calendar.DATE, amount);
        }
        if (endOfDay) {
            return endOfDay(cal.getTime());
        }
        return cal.getTime();
    }

    /**
     * Returns the maximum value of 2 dates.
     *
     * @param date1
     * @param date2
     * @return The max of the 2 dates. If one date is null, the other date is
     * returned. If both dates are null, null is returned.
     */
    public static Date maxDate(Date date1, Date date2) {
        if ((date1 == null && date2 == null) || date2 == null) {
            return date1;
        }
        // Compare the milliseconds of the date because the CompareTo function is unreliable when
        // comparing java.util.Date with java.sql.Date
        if (date1 != null && date1.getTime() > date2.getTime()) {
            return date1;
        }
        return date2;
    }

    /**
     * Returns the current date time.
     *
     * @return
     */
    public static Date now() {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            return calendar.getTime();
        }
    }

    /**
     * Returns a new date object based on the year, month and day provided. The
     * date is created using a calender to ensure localization of the date
     * created.
     *
     * @param year Year for the new date
     * @param month Month of the new date. Should be 1 (Jan) to 12 (Dec)
     * @param day Day for the new date
     * @return The new date using
     */
    public static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            // Java uses a 0 based array for identifying the month.
            calendar.clear();
            calendar.set(year, month - 1, day);
            return calendar.getTime();
        }
    }

    /**
     * Uses SimpleDateFormat to format the current datetime.
     *
     * @param format The format to use for the date
     * @return The formatted date string.
     */
    public static String simpleFormat(String format) {
        return simpleFormat(now(), format);
    }

    /**
     * Uses SimpleDateFormat to format the specified datetime.
     *
     * @param date The date to format.
     * @param format The format to use for the date.
     * @return The formatted date string.
     */
    public static String simpleFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Returns medium date string representation in localized format (e.g.
     * 02/11/2012).
     *
     * @param date Date to convert into string
     * @param includeTime Indicates whether to append time string or not. If
     * true, time string will be appended in short format (e.g. 11:01)
     */
    public static String getMediumDateString(Date date, boolean includeTime) {
        if (includeTime) {
            return getDateTimeString(date, DateFormat.MEDIUM, DateFormat.SHORT);
        } else {
            return getDateString(date, DateFormat.MEDIUM);
        }
    }

    /**
     * Returns long date string representation in localized format (e.g.
     * February 11, 2012).
     *
     * @param date Date to convert into string
     * @param includeTime Indicates whether to append time string or not. If
     * true, time string will be appended in short format (e.g. 11:01)
     */
    public static String getLongDateString(Date date, boolean includeTime) {
        if (includeTime) {
            return getDateTimeString(date, DateFormat.LONG, DateFormat.SHORT);
        } else {
            return getDateString(date, DateFormat.LONG);
        }
    }

    /**
     * Returns short date string representation in localized format (e.g.
     * 02/11/12).
     *
     * @param date Date to convert into string
     * @param includeTime Indicates whether to append time string or not. If
     * true, time string will be appended in short format (e.g. 11:01)
     */
    public static String getShortDateString(Date date, boolean includeTime) {
        if (includeTime) {
            return getDateTimeString(date, DateFormat.SHORT, DateFormat.SHORT);
        } else {
            return getDateString(date, DateFormat.SHORT);
        }
    }

    /**
     * Returns date string representation in localized format.
     *
     * @param date Date to convert into string
     * @param dateStyle Date style format (DateFormat.SHORT, DateFormat.MEDIUM,
     * DateFormat.LONG)
     */
    public static String getDateString(Date date, int dateStyle) {
        if (date == null) {
            return "";
        }
        DateFormat f = DateFormat.getDateInstance(dateStyle);
        return f.format(date);
    }

    /**
     * Returns date and time string representation in localized format.
     *
     * @param date Date to convert into string
     * @param dateStyle Date style format (DateFormat.SHORT, DateFormat.MEDIUM,
     * DateFormat.LONG)
     * @param timeStyle Time style format (DateFormat.SHORT, DateFormat.MEDIUM,
     * DateFormat.LONG)
     */
    public static String getDateTimeString(Date date, int dateStyle, int timeStyle) {
        if (date == null) {
            return "";
        }
        DateFormat f = DateFormat.getDateTimeInstance(dateStyle, timeStyle);
        return f.format(date);
    }

    /**
     * Returns the day after
     * <code>date</code>.
     *
     * @param date Date used in calculating next day
     * @return Day after <code>date</code>.
     */
    public static long nextDay(long date) {
        return addDays(date, 1);
    }

    /**
     * Returns the week after
     * <code>date</code>.
     *
     * @param date Date used in calculating next week
     * @return week after <code>date</code>.
     */
    public static long nextWeek(long date) {
        return addDays(date, 7);
    }

    /**
     * Returns the number of days difference between
     * <code>t1</code> and
     * <code>t2</code>.
     *
     * @param t1 Time 1
     * @param t2 Time 2
     * @param checkOverflow indicates whether to check for overflow
     * @return Number of days between <code>start</code> and <code>end</code>
     */
    public static int getDaysDiff(long t1, long t2, boolean checkOverflow) {
        if (t1 > t2) {
            long tmp = t1;
            t1 = t2;
            t2 = tmp;
        }
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(t1);
            int delta = 0;
            while (calendar.getTimeInMillis() < t2) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                delta++;
            }
            if (checkOverflow && (calendar.getTimeInMillis() > t2)) {
                delta--;
            }
            return delta;
        }
    }

    /**
     * Returns the number of days difference between
     * <code>t1</code> and
     * <code>t2</code>.
     *
     * @param t1 Time 1
     * @param t2 Time 2
     * @return Number of days between <code>start</code> and <code>end</code>
     */
    public static int getDaysDiff(long t1, long t2) {
        return getDaysDiff(t1, t2, true);
    }

    /**
     * Check, whether the date passed in is the first day of the year.
     *
     * @param date date to check in millis
     * @return <code>true</code> if <var>date</var> corresponds to the first day
     * of a year
     * @see Date#getTime()
     */
    public static boolean isFirstOfYear(long date) {
        boolean ret = false;
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            int currentYear = calendar.get(Calendar.YEAR);
            // Check yesterday
            calendar.add(Calendar.DATE, -1);
            int yesterdayYear = calendar.get(Calendar.YEAR);
            ret = (currentYear != yesterdayYear);
        }
        return ret;
    }

    /**
     * Check, whether the date passed in is the first day of the month.
     *
     * @param date date to check in millis
     * @return <code>true</code> if <var>date</var> corresponds to the first day
     * of a month
     * @see Date#getTime()
     */
    public static boolean isFirstOfMonth(long date) {
        boolean ret = false;
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            int currentMonth = calendar.get(Calendar.MONTH);
            // Check yesterday
            calendar.add(Calendar.DATE, -1);
            int yesterdayMonth = calendar.get(Calendar.MONTH);
            ret = (currentMonth != yesterdayMonth);
        }
        return ret;
    }

    /**
     * Returns the day before
     * <code>date</code>.
     *
     * @param date Date used in calculating previous day
     * @return Day before <code>date</code>.
     */
    public static long previousDay(long date) {
        return addDays(date, -1);
    }

    /**
     * Returns the week before
     * <code>date</code>.
     *
     * @param date Date used in calculating previous week
     * @return week before <code>date</code>.
     */
    public static long previousWeek(long date) {
        return addDays(date, -7);
    }

    /**
     * Returns the first day before
     * <code>date</code> that has the day of week matching
     * <code>startOfWeek</code>. For example, if you want to find the previous
     * monday relative to
     * <code>date</code> you would call
     * <code>getPreviousDay(date, Calendar.MONDAY)</code>.
     *
     * @param date Base date
     * @param startOfWeek Calendar constant correspoding to start of week.
     * @return start of week, return value will have 0 hours, 0 minutes, 0
     * seconds and 0 ms.
     *
     */
    public static long getPreviousDay(long date, int startOfWeek) {
        return getDay(date, startOfWeek, -1);
    }

    /**
     * Returns the first day after
     * <code>date</code> that has the day of week matching
     * <code>startOfWeek</code>. For example, if you want to find the next
     * monday relative to
     * <code>date</code> you would call
     * <code>getPreviousDay(date, Calendar.MONDAY)</code>.
     *
     * @param date Base date
     * @param startOfWeek Calendar constant correspoding to start of week.
     * @return start of week, return value will have 0 hours, 0 minutes, 0
     * seconds and 0 ms.
     *
     */
    public static long getNextDay(long date, int startOfWeek) {
        return getDay(date, startOfWeek, 1);
    }

    private static long getDay(long date, int startOfWeek, int increment) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            // Normalize the view starting date to a week starting day
            while (day != startOfWeek) {
                calendar.add(Calendar.DATE, increment);
                day = calendar.get(Calendar.DAY_OF_WEEK);
            }
            return startOfDayInMillis(calendar.getTimeInMillis());
        }
    }

    /**
     * Returns the previous month.
     *
     * @param date Base date
     * @return previous month
     */
    public static long getPreviousMonth(long date) {
        return incrementMonth(date, -1);
    }

    /**
     * Returns the next month.
     *
     * @param date Base date
     * @return next month
     */
    public static long getNextMonth(long date) {
        return incrementMonth(date, 1);
    }

    private static long incrementMonth(long date, int increment) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            calendar.add(Calendar.MONTH, increment);
            return calendar.getTimeInMillis();
        }
    }

    /**
     * Returns the date corresponding to the start of the month.
     *
     * @param date Base date
     * @return Start of month.
     */
    public static long getStartOfMonth(long date) {
        return getMonth(date, -1);
    }

    /**
     * Returns the date corresponding to the end of the month.
     *
     * @param date Base date
     * @return End of month.
     */
    public static long getEndOfMonth(long date) {
        return getMonth(date, 1);
    }

    private static long getMonth(long date, int increment) {
        long result;
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            if (increment == -1) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                result = startOfDayInMillis(calendar.getTimeInMillis());
            } else {
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.add(Calendar.MILLISECOND, -1);
                result = calendar.getTimeInMillis();
            }
        }
        return result;
    }

    /**
     * Returns the day of the week.
     *
     * @param date date
     * @return day of week.
     */
    public static int getDayOfWeek(long date) {
        Calendar calendar = Calendar.getInstance();
        synchronized (calendar) {
            calendar.setTimeInMillis(date);
            return (calendar.get(Calendar.DAY_OF_WEEK));
        }
    }
}