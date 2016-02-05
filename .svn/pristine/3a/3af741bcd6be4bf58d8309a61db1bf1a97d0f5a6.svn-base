package com.zzg.robotium.lib;

import android.util.Log;

import java.util.Calendar;

public class CommonLib {

    public static String getCurrentTime() {
        try {
            Calendar ca = Calendar.getInstance();
            int year = ca.get(Calendar.YEAR);
            int month = ca.get(Calendar.MONTH);
            int day = ca.get(Calendar.DATE);
            int minute = ca.get(Calendar.MINUTE);
            int hour = ca.get(Calendar.HOUR);
            int second = ca.get(Calendar.SECOND);
            String currentTime = (String.valueOf(year) + "-" + String.valueOf(month + 1) + "-"
                    + String.valueOf(day) + "-" + String.valueOf(hour) + "-"
                    + String.valueOf(minute) + "-" + String.valueOf(second));
            Log.i(NewSolo.Tag, "The current time is: " + currentTime);
            return currentTime;
        } catch (Exception e) {
            Log.e(NewSolo.Tag, "Exceptionet the current time ");
            return "00000000";
        }
    }

}

	


