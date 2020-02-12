package api;

import gen.CalendarData;

import java.util.Calendar;
import java.util.Date;

public abstract class CalendarAPI {

    private static Calendar calendar;

    private final String apiURL = "";

    public static void Init(){
        calendar = Calendar.getInstance();

    }


    public static CalendarData GetCalendarData() {

        CalendarData data = new CalendarData();
        data.date = System.currentTimeMillis();
        data.isLecture = isWeekend();
        data.isWeekday = !isWeekend();
        data.isNationalHoliday = false;
        data.isUniversityHoliday = isWeekend();

        return data;
    }

    private static boolean isWeekend() {
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    private static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }



}
