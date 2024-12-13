package com.theradiary.ispwtheradiary.engineering.enums;

public enum DayOfTheWeek {
    MONDAY("Lunedì"),
    TUESDAY("Martedì"),
    WEDNESDAY("Mercoledì"),
    THURSDAY("Giovedì"),
    FRIDAY("Venerdì");

    private String day;

    DayOfTheWeek(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }
}
