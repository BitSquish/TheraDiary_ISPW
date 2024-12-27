package com.theradiary.ispwtheradiary.engineering.enums;

public enum DayOfTheWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5);


    private static final String LUNE = "Lunedì";
    private static final String MART = "Martedì";
    private static final String MERC = "Mercoledì";
    private static  final String GIOV = "Giovedì";
    private  static final String VENE = "Venerdì";

    private final int id;

    DayOfTheWeek(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static String translateDay(int id){
        if(id<1 || id>5){
            return "Giorno non valido";
        }
        return switch (id) {
            case 1 -> LUNE;
            case 2 -> MART;
            case 3 -> MERC;
            case 4 -> GIOV;
            case 5 -> VENE;
            default -> "Giorno non valido";
        };
    }

    public static DayOfTheWeek fromStringToDay(String day){
        return switch (day){
            case LUNE,"MONDAY" -> MONDAY;
            case MART,"TUESDAY" -> TUESDAY;
            case MERC,"WEDNESDAY" -> WEDNESDAY;
            case GIOV,"THURSDAY" -> THURSDAY;
            case VENE,"FRIDAY" -> FRIDAY;
            default -> null;
        };
    }
    public static String fromDayToString(DayOfTheWeek day){
        return switch (day){
            case MONDAY -> LUNE;
            case TUESDAY -> MART;
            case WEDNESDAY -> MERC;
            case THURSDAY -> GIOV;
            case FRIDAY -> VENE;
        };
    }

}
