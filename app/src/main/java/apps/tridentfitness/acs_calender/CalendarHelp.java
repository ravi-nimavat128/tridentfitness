package apps.tridentfitness.acs_calender;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarHelp {
    public static String getMothStr(int i) {
        String str = "";
        switch (i) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEPT";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return str;
        }
    }

    public static String getSimpleWeekStr(int i) {
        String str = "";
        switch (i) {
            case 1:
                return "S";
            case 2:
                return "M";
            case 3:
                return "T";
            case 4:
                return "W";
            case 5:
                return "T";
            case 6:
                return "F";
            case 7:
                return "S";
            default:
                return str;
        }
    }

    public static String getWeekStr(int i) {
        String str = "";
        switch (i) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return str;
        }
    }

    public static String getDoubleNumberStr(int i) {
        if (i >= 10) {
            return String.valueOf(i);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0");
        stringBuilder.append(i);
        return stringBuilder.toString();
    }

    public static int getDayNumberOfWeek(Calendar calendar) {
        return calendar.get(7);
    }

    public static int getMonthOfYear(Calendar calendar) {
        return calendar.get(2) + 1;
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(1);
    }

    public static int getDayNumberOfMonth(Calendar calendar) {
        return calendar.get(5);
    }

    public static int getHourOfDay(Calendar calendar) {
        return calendar.get(11);
    }

    public static int getMinuteOfHour(Calendar calendar) {
        return calendar.get(12);
    }

    public static Calendar getOneNumberCalendarOfPerMonth(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.set(5, (instance.get(5) - instance.get(5)) + 1);
        return instance;
    }

    public static Calendar getCurrentCalendar() {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.setTimeZone(TimeZone.getDefault());
        return instance;
    }

    public static Calendar getCalendar(Calendar calendar, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.add(6, i);
        return instance;
    }

    public static Calendar getBeforeYear(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.get(1);
        instance.add(1, -1);
        return instance;
    }

    public static Calendar getBeforeMonth(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.get(2);
        instance.add(2, -1);
        return instance;
    }

    public static Calendar getNextMonth(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.add(2, 1);
        return instance;
    }

    public static Calendar getMonth(Calendar calendar, int i, int i2) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        instance.add(2, -(i - i2));
        return instance;
    }

    public static boolean isToday(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date).equals(simpleDateFormat.format(new Date()));
    }
}
