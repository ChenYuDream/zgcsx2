package org.jypj.zgcsx.course.enums;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum WeekDay {
    MONDAY(DayOfWeek.MONDAY, 1, "周一", "星期一"),
    TUESDAY(DayOfWeek.TUESDAY, 2, "周二", "星期二"),
    WEDNESDAY(DayOfWeek.WEDNESDAY, 3, "周三", "星期三"),
    THURSDAY(DayOfWeek.THURSDAY, 4, "周四", "星期四"),
    FRIDAY(DayOfWeek.FRIDAY, 5, "周五", "星期五"),
    SATURDAY(DayOfWeek.SATURDAY, 6, "周六", "星期六"),
    SUNDAY(DayOfWeek.SUNDAY, 0, "周日", "星期日");
    private DayOfWeek dayOfWeek;
    private int code;
    private String text;
    private String fullText;

    WeekDay(DayOfWeek dayOfWeek, Integer code, String text, String fullText) {
        this.dayOfWeek = dayOfWeek;
        this.code = code;
        this.text = text;
        this.fullText = fullText;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public static WeekDay getByDayOfWeek(DayOfWeek dayOfWeek) {
        return Arrays.stream(WeekDay.values()).filter(day -> day.getDayOfWeek() == dayOfWeek).collect(Collectors.toList()).get(0);
    }
}
