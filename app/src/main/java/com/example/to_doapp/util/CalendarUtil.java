package com.example.to_doapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

}
