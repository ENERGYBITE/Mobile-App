package com.ecar.energybite.util;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"DefaultLocale", "SimpleDateFormat"})
public final class DateUtility {
    public static long MILIS_IN_DAY = 24L * 60L * 60L * 1000L;
    public static long APPROX_MILIS_IN_MONTH = MILIS_IN_DAY * 30L;
    public static long APPROX_MILIS_IN_YEAR = MILIS_IN_DAY * 365L;

    public static int SECONDS_IN_DAY = 24 * 60 * 60;
    public static int APPROX_SECONDS_IN_MONTH = SECONDS_IN_DAY * 30;
    public static int APPROX_SECONDS_IN_YEAR = SECONDS_IN_DAY * 365;
    public static long MILISECONDS_IN_HOUR = 1L * 60L * 60L * 1000L;
    public static long MILISECONDS_IN_MINUTE = 1L * 60L * 1000L;

    public static String[] months = new String[]{"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
    public static String[] dayOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday",
            "ThursDay", "Friday", "Saturday"};

    public static String getDayOfWeek(int weekDay) {
        if (weekDay <= 7) {
            return dayOfWeek[weekDay - 1];
        } else {
            return "";
        }

    }

    public static String getMonth(int month) {
        if (month < 12) {
            return months[month];
        } else {
            return "";
        }

    }

    public static String getYearAsYY(Calendar cal) {
        if (cal != null) {
            return "" + (cal.get(Calendar.YEAR) % 100);
        }
        return "";
    }

    public static String getDateAsDD(Calendar cal) {
        if (cal != null) {
            int date = cal.get(Calendar.DATE);
            return String.format("%02d", date);
        }
        return "";
    }

    public static String getDateInEEEDDMMMYY(Calendar dt) {
        String month = DateUtility.getMonth(dt.get(Calendar.MONTH)).substring(0, 3);
        String year = String.valueOf(dt.get(Calendar.YEAR)).substring(2, 4);
        String day = getDayOfWeek(dt.get(Calendar.DAY_OF_WEEK)).substring(0, 3);
        String displayDate = day + " " + String.format("%02d", dt.get(Calendar.DATE)) + " " + month + "'" + year;
        return displayDate;
    }

    public static String getDateInDDMMM(Calendar dt) {
        String date = String.format("%02d", dt.get(Calendar.DATE));
        String month = DateUtility.getMonth(dt.get(Calendar.MONTH)).substring(0, 3);
        String displayDate = date + " " + month;
        return displayDate;
    }

    public static Spanned getDateInDDMMMYYPipeWithBoldHHMM(Calendar dt) {
        String date = String.valueOf(dt.get(Calendar.DATE));
        String month = DateUtility.getMonth(dt.get(Calendar.MONTH)).substring(0, 3);
        String year = String.valueOf(dt.get(Calendar.YEAR));
        String day = getDayOfWeek(dt.get(Calendar.DAY_OF_WEEK)).substring(0, 3).toUpperCase();
        String hr = String.valueOf(dt.get(Calendar.HOUR_OF_DAY));
        hr = hr.length() == 2 ? hr : "0" + hr;
        String min = String.valueOf(dt.get(Calendar.MINUTE));
        min = min.length() == 2 ? min : "0" + min;
        String displayDate = day + " " + date + "  " + month + "' " + year + " |<font color=\"#500000\"> <b>" + hr
                + ":" + min + "</b></font>";
        return Html.fromHtml(displayDate);
    }

    public static String getDurationHHMM(long duration) {
        String hrs = String.valueOf(duration / 60);
        String min = String.valueOf(duration % 60);
        if (hrs.length() < 2) {
            hrs = "0" + hrs;
        }
        if (min.length() < 2) {
            min = "0" + min;
        }
        return hrs + "h " + min + "m";

    }

    public static String getTimeHHMM(Calendar cal) {
        String timeString = "";
        timeString = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
        return timeString;
    }

    public static String getTimeHHhrsMMmin(Calendar cal) {
        String timeString = "";
        timeString = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + "h " + String.format("%02d", cal.get(Calendar.MINUTE)) + "m";
        return timeString;
    }

    public static String getDurationAsString(long duration) {
        long days = duration / 1440;
        duration = duration % 1440;
        long hours = duration / 60;
        duration = duration % 60;
        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(String.format("%02d", days)).append("d");
        }
        if (hours > 0) {
            builder.append(String.format("%02d", hours)).append("h");
        }
        if (duration > 0) {
            builder.append(String.format("%02d", duration)).append("m");
        }
        return builder.toString();
    }


    public static String getDateInDDMMYYYYWithUnsetOnNull(Calendar dt) {
        if (dt == null) {
            return "Unset";
        }
        return getDateInDDMMYYYYWithUnsetOnNull(dt.getTime());
    }

    public static String getDateInDDMMYYYYWithUnsetOnNull(Date dt) {
        if (dt == null) {
            return "Unset";
        }
        return getDateInDDMMYYYY(dt);
    }

    public static String getDateInDDMMMYY(Calendar dt) {
        if (dt == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
        return sdf.format(dt.getTime());
    }

    public static String getDateInDDMMYYYY(Date dt) {
        if (dt == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dt);
    }

    public static String getDateInDDMMMYYYY(Date dt) {
        if (dt == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(dt);
    }

    public static String getDateInDDMMM(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
        return sdf.format(dt);
    }

    public static String getDateInEEEDDSpaceMMM(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM");
        return sdf.format(dt);
    }

    public static String getDateInDDSpaceMMM(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        return sdf.format(dt);
    }

    public static String getDateInDDMMBREEE(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return sdf.format(dt) + "<br/>" + getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK)).subSequence(0, 3);
    }

    public static String getDateInDDMMYYHHMM(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(dt);
    }

    public static String getDateInHHMM(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dt);
    }

    public static String getDateDDMMYYYY(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dt);
    }

    public static String getDateDDMMYYYYCheckHHMMUnset(Calendar cal) {

        if (cal != null && cal.getTime() != null) {
            if ((cal.get(Calendar.MINUTE) > 0) || (cal.get(Calendar.HOUR_OF_DAY) > 0) || (cal.get(Calendar.HOUR) > 0)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                return sdf.format(cal.getTime());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTimeInddMMyyyyHHmmss(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getDateDDMMYYYY(Calendar cal) {

        if (cal != null && cal.getTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    public static String getDateMMYYYY(Calendar cal) {

        if (cal != null && cal.getTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    public static String getDateDDMMYYYYNEWLINEHHMMIfNullUnset(Calendar dt) {
        if (dt == null) {
            return "Unset";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'\n'HH:mm");
        return sdf.format(dt.getTime());
    }

    public static String getDateInHHMMSSDDMMYYT(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd/MM/yyyy");
        return sdf.format(dt);
    }

    public static String getDateInDDMMYYYYTHHMMSS(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy h:mm:ss a");
        return sdf.format(dt);
    }

    public static Calendar clearTimeFields(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Date getDateWithZeroTimefields(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar getCalendarInDDMMYYNullIfError(String str) {
        Calendar cal = Calendar.getInstance();
        if (str == null || "dd/mm/yyyythh:mm".equalsIgnoreCase(str)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            cal.setTime(sdf.parse(str));
            return cal;
        } catch (Exception e) {
            return null;
        }

    }

    public static boolean isSameDate(Calendar date1, Calendar date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        int year1 = date1.get(Calendar.YEAR);
        int year2 = date2.get(Calendar.YEAR);
        if (year1 != year2) {
            return false;
        }
        int dayOfYear1 = date1.get(Calendar.DAY_OF_YEAR);
        int dayOfYear2 = date2.get(Calendar.DAY_OF_YEAR);
        return dayOfYear1 == dayOfYear2;
    }

    public static long getDifferenceInDates(Calendar cal1, Calendar cal2, int differenceUnit) {
        if (cal1.after(cal2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = cal1;
            cal1 = cal2;
            cal2 = swap;
        }
        return getDifferenceInDatesWithoutSwap(cal2, cal1, differenceUnit);
    }

    public static long getDifferenceInDatesWithoutSwap(Calendar cal1, Calendar cal2, int differenceUnit) {
        long diff = cal1.getTimeInMillis() - cal2.getTimeInMillis();
        switch (differenceUnit) {
            case Calendar.SECOND:
                return diff / 1000L;
            case Calendar.MINUTE:
                return diff / (60L * 1000L);
            case Calendar.HOUR:
                return diff / (60L * 60L * 1000L);
            case Calendar.DATE:
                return diff / (24L * 60L * 60L * 1000L);
            case Calendar.MONTH:
                return diff / (30L * 24L * 60L * 60L * 1000L);
            case Calendar.YEAR:
                return diff / (365L * 24L * 60L * 60L * 1000L);
        }
        throw new RuntimeException("Invalid Difference Unit Value: " + differenceUnit);
    }

    public static Date parseDateInDDMYYYYHHMMNullIfError(String str) {
        if (str == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateInYYYYMMDDHHMMNullIfError(String str) {
        if (str == null) {
            return null;
        }
        try {
            str = str.replaceAll("-", "/");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseDateInDDMMYYYY(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(str);
    }

    public static Calendar getCalendar(Calendar cal) {
        if (cal != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(cal.getTime());
            return cal1;
        }
        return null;
    }

    public static Calendar getCalendarWithClearTimeStamp(Calendar cal) {
        Calendar cal1 = Calendar.getInstance();
        if (cal != null) {
            cal1.setTime(cal.getTime());
            return clearTimeFields(cal1);
        }
        return cal;
    }

    public static Calendar addInNewCalendar(Calendar cal, int calUnit, int unitToAdd) {
        Calendar cal1 = getCalendar(cal);
        if (cal1 != null) {
            cal1.add(calUnit, unitToAdd);
        }
        return cal1;
    }

    public static Calendar add(Calendar cal, int calUnit, int unitToAdd) {
        if (cal != null) {
            cal.add(calUnit, unitToAdd);
        }
        return cal;
    }

    public static String[] getDayNameArray(String dayNameFormat, Locale locale, boolean isStartWeekWithSunday) {
        String[] dayNames = new String[7];
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(dayNameFormat, locale);
        cal.set(Calendar.DAY_OF_WEEK, isStartWeekWithSunday ? 1 : 2);
        for (int dayIndex = 0; dayIndex < 7; dayIndex++, cal.add(Calendar.DAY_OF_WEEK, 1)) {
            dayNames[dayIndex] = format.format(cal.getTime());
        }
        return dayNames;
    }

    public static String getMonthYearAsMMMYY(Calendar calendar) {
        if (calendar != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");
            return sdf.format(calendar.getTime());
        }
        return "";
    }

    public static String getDateHHMMDDMMM(Calendar cal) {

        if (cal != null && cal.getTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE dd, MMM");
            return sdf.format(cal.getTime());
        }
        return "";
    }

    @Nullable
    public static Calendar getParsedDateAsCalendar(String date) {
        if (StringUtility.isNull(date)) {
            return null;
        }
        String dateFormat = null;
        if (date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
            dateFormat = "dd/MM/yyyy";
        } else if (date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}, [0-9]{2}:[0-9]{2}")) {
            dateFormat = "dd/MM/yyyy, hh:mm";
        } else if (date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}, [0-9]{2}:[0-9]{2}:[0-9]{2}")) {
            dateFormat = "dd/MM/yyyy, hh:mm:ss";
        } else if (date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}")) {
            dateFormat = "dd/MM/yyyy hh:mm:ss";
        }

        try {
            Date parsedDate = new Date(0);
            if (dateFormat != null) {
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                parsedDate = format.parse(date);
            } else if (StringUtility.isNumeric(date)) {
                parsedDate = new Date(Long.parseLong(date));
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            return cal;
        } catch (ParseException e) {
            Log.e(DateUtility.class.getCanonicalName(), "Error while parsing Calender", e);
        }
        return null;
    }

    private DateUtility() {
    }

    public static String getDateYYYY() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static Date convertServerTimeToDate(String str) {
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt = sdf.parse(str);
            return dt;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
