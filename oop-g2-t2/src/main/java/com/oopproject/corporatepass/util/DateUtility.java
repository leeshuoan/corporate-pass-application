package com.oopproject.corporatepass.util;

// w ww.  j a  va 2  s. c o  m
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/* 
 *    D E E P B L A C K    B L O G    L I C E N S E
 * 
 *
 * Copyright (c) 2001-2003 Timothy J. Kettering  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The names "deepBlack" must not be used to endorse or promote 
 *    products derived from this software without prior written 
 *    permission. For written permission, please contact 
 *    the copyright holder at tim@blackcore.com.
 *
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 */
import java.util.concurrent.TimeUnit;

/**
 * Date utility class file.
 * 
 * @author timster@blackcore.com Nov 17, 2002
 * 
 * @todo - Only the timestamp parameter methods have been tested somewhat
 *       extensively to be sure they return correct timestamps. The string and
 *       integer parameter methods need to be refined for accurate behavior, and
 *       better failsafes.
 * 
 */
public class DateUtility {

    /**
     * Returns a primitive long which represents the timestamp of the first
     * millisecond of the month matching the supplied parameters. This method
     * assumes the default timezone.
     * 
     * @param year
     * @param month
     * @return long
     */
    static public long getFirstMilliOfMonth(String year, String month) {
        return getFirstMilliOfMonth(year, month, TimeZone.getDefault());
    }

    /**
     * Returns a primitive long which represents the timestamp of the last
     * millisecond of the month matching the supplied parameters. This method
     * assumes the default timezone.
     * 
     * @param year
     * @param month
     * @return long
     */
    static public long getLastMilliOfMonth(String year, String month) {
        return getLastMilliOfMonth(year, month, TimeZone.getDefault());
    }

    /**
     * This utility returns the first millsecond of the month, and year, and
     * timezone supplied as arguments.
     * 
     * @param String
     *                 month
     * @param String
     *                 year
     * @param TimeZone
     *                 tz
     * @return long
     */
    static public long getFirstMilliOfMonth(String year, String month, TimeZone tz) {
        return getFirstMilliOfMonth(Integer.parseInt(year),
                Integer.parseInt(month), tz);
    }

    /**
     * This utility returns the first millsecond of the month, and year, and
     * timezone supplied as arguments.
     * 
     * @param int      month
     * @param int      year
     * @param TimeZone
     *                 tz
     * @return long
     */
    static public long getFirstMilliOfMonth(int year, int month, TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar(year, (month - 1), 1);
        cal.setTimeZone(tz);
        return cal.getTime().getTime();
    }

    /**
     * This will retun the first millisecond of the month that contains the
     * timestamp provided
     * 
     * @param timestamp
     * @return long
     */
    static public long getFirstMilliOfMonth(long timestamp, TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(tz);
        cal.setTime(new Date(timestamp));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date date = cal.getTime();

        return date.getTime();
    }

    /**
     * Gets the last millsecond of the month, according to the timestamp and
     * timezone arguments.
     * 
     * @param timestamp
     * @param tz
     * @return long
     */
    static public long getLastMilliOfMonth(long timestamp, TimeZone tz) {
        timestamp = getFirstMilliOfMonth(timestamp, tz);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(tz);
        cal.setTime(new Date(timestamp));

        // now we'll roll the calendar forward one month.
        // in the case of december, we need to roll forward the year too
        if (cal.get(GregorianCalendar.MONTH) == GregorianCalendar.DECEMBER) {
            cal.roll(GregorianCalendar.YEAR, true);
        }

        cal.roll(GregorianCalendar.MONTH, true);

        long date = cal.getTime().getTime();

        date = date - 1L;
        return date;
    }

    /**
     * This utility returns the last millsecond of the month, and year, and
     * timezone supplied as arguments.
     * 
     * @param String
     *                 month
     * @param String
     *                 year
     * @param TimeZone
     *                 tz
     * @return long
     */
    static public long getLastMilliOfMonth(String year, String month, TimeZone tz) {
        long time = getLastMilliOfMonth(Integer.parseInt(year),
                Integer.parseInt(month), tz);
        return time;
    }

    /**
     * This utility returns the last millsecond of the month, and year, and
     * timezone supplied as arguments.
     * 
     * @param int      month
     * @param int      year
     * @param TimeZone
     *                 tz
     * @return long
     */
    static public long getLastMilliOfMonth(int year, int month, TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar(year, (month - 1), 1);
        cal.setTimeZone(tz);

        // set the maximum last day
        int lastday = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        cal.set(GregorianCalendar.DAY_OF_MONTH, lastday);

        // set other calendar maximums. - we should do this programatically
        // too but i'm too lazy, and i dont think they're gonna change the gregorian
        // calendar anytime soon.. eh?
        cal.set(GregorianCalendar.HOUR_OF_DAY, 23);
        cal.set(GregorianCalendar.MINUTE, 59);
        cal.set(GregorianCalendar.SECOND, 59);
        cal.set(GregorianCalendar.MILLISECOND, 999);

        long time = cal.getTime().getTime();
        return time;
    }

    static public long getFirstMilliOfYear(String year) {
        return getFirstMilliOfYear(year, TimeZone.getDefault());
    }

    static public long getFirstMilliOfYear(String year, TimeZone tz) {
        return getFirstMilliOfYear(Integer.parseInt(year), tz);
    }

    static public long getFirstMilliOfYear(int year, TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar(year, 0, 1);
        cal.setTimeZone(tz);
        return cal.getTime().getTime();
    }

    static public long getLastMilliOfYear(String year) {
        return getLastMilliOfYear(year, TimeZone.getDefault());
    }

    static public long getLastMilliOfYear(String year, TimeZone tz) {
        return getLastMilliOfYear(Integer.parseInt(year), tz);
    }

    static public long getLastMilliOfYear(int year, TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar(year, 11, 1);
        cal.setTimeZone(tz);

        // set the maximum last day
        int lastday = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        cal.set(GregorianCalendar.DAY_OF_MONTH, lastday);

        // set other calendar maximums. - we should do this programatically
        // too but i'm too lazy, and i dont think they're gonna change the gregorian
        // calendar anytime soon.. eh?
        cal.set(GregorianCalendar.HOUR_OF_DAY, 23);
        cal.set(GregorianCalendar.MINUTE, 59);
        cal.set(GregorianCalendar.SECOND, 59);
        cal.set(GregorianCalendar.MILLISECOND, 999);

        long time = cal.getTime().getTime();
        return time;
    }

    /**
     * This utility returns the difference of 2 dates in days.
     * 
     * @param Date      d1
     * @param Date      d2
     * @return int
     */
    static public int getDaysBetween(Date d1, Date d2) {
        long diff = Math.abs(d2.getTime() - d1.getTime());
        long plus = d1.toString().equals(d2.toString()) ? 0 : 1000 * 60 * 60 * 24;
        return (int) ((diff+plus) / (1000 * 60 * 60 * 24));
    }

    static public int getCurrentMonth() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getDefault());
        return (cal.get(Calendar.MONTH) + 1);
    }

    static public long getDateEightWeeksFromNow(TimeZone tz) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeZone(tz);
        cal.add(Calendar.WEEK_OF_YEAR, 8);
        return cal.getTime().getTime();
    }
}
