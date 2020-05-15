package com.example.to_doapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {

    public static String getCurrentDate() {
        Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd - MM - yyyy");
        return formatter.format(currDate);
    }

    public static long convertStringToMilliseconds(String inputDate) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd - MM - yyyy");
        try {
            date = formatter.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String convertDateFormat(String currentDate, String targetFormat, String currentFormat) {
        String targetFormatDate;
        DateFormat originalFormatter = new SimpleDateFormat(currentFormat, Locale.ENGLISH);
        DateFormat targetFormatter = new SimpleDateFormat(targetFormat);
        try {
            Date date = originalFormatter.parse(currentDate);
            targetFormatDate = targetFormatter.format(date);
            return targetFormatDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
