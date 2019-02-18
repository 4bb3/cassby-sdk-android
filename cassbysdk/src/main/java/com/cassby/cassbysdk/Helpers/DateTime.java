package com.cassby.cassbysdk.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

    public static String getCurrentDayAndTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return date.format(Calendar.getInstance().getTime());
    }

}
