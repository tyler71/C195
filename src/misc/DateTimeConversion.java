package misc;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeConversion {
    private String fds = "MM-dd-yyyy_HH-MM"; // 06-25-1950_23-10
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(fds, Locale.getDefault());
    private ZonedDateTime zdt = dateFormatter.parse("01-05-2020_20-20", ZonedDateTime :: from);
    private LocalDate date;

    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;



    public DateTimeConversion(int month, int day, int year, int hour, int minute) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public ZonedDateTime getDate() throws ParseException {
        String parsedMonth = getMonth();
        String parsedDay = getDay();
        String parsedYear = getYear();
        String parsedHour = getHour();
        String parsedMinute = getMinute();
        String parsedString = parsedMonth + '-' + parsedDay + '-'
                + parsedYear + '_' + parsedHour + '-' + parsedMinute;
        ZonedDateTime formattedDate = dateFormatter.parse(parsedString, ZonedDateTime :: from);
        return formattedDate;
    }

    private String appendLeadingZero(int value) {
        String parsedValue = String.valueOf(value);
        if(value < 10 && value > 0) {
            parsedValue = "0" + parsedValue;
        }
        return parsedValue;
    }

    public String getMonth() {
        return appendLeadingZero(month);
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay() {
        return appendLeadingZero(day);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getYear() {
        return appendLeadingZero(year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getHour() {
        return appendLeadingZero(hour);
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return appendLeadingZero(minute);
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}



