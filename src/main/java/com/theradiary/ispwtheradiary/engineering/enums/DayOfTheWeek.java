package com.theradiary.ispwtheradiary.engineering.enums;

public enum DayOfTheWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);

    private int id;

    DayOfTheWeek(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static String translateDay(int id){
        return switch (id) {
            case 1 -> "Lunedì";
            case 2 -> "Martedì";
            case 3 -> "Mercoledì";
            case 4 -> "Giovedì";
            case 5 -> "Venerdì";
            default -> null;
        };
    }

    public static DayOfTheWeek fromStringToDay(String day){
        return switch (day){
            case "Lunedì" -> MONDAY;
            case "Martedì" -> TUESDAY;
            case "Mercoledì" -> WEDNESDAY;
            case "Giovedì" -> THURSDAY;
            case "Venerdì" -> FRIDAY;
            default -> null;
        };
    }
}
