package com.theradiary.ispwtheradiary.engineering.enums;

public enum TimeSlot {
    NINE_TEN(1),
    TEN_ELEVEN(2),
    ELEVEN_TWELVE(3),
    TWELVE_THIRTEEN(4),
    FOURTEEN_FIFTEEN(5),
    FIFTEEN_SIXTEEN(6),
    SIXTEEN_SEVENTEEN(7);

    private int id;

    public int getId() {
        return id;
    }

    TimeSlot(int id) {
        this.id = id;
    }

    public static String translateTimeSlot(int id){
        return switch (id) {
            case 1 -> "9:00 - 10:00";
            case 2 -> "10:00 - 11:00";
            case 3 -> "11:00 - 12:00";
            case 4 -> "12:00 - 13:00";
            case 5 -> "14:00 - 15:00";
            case 6 -> "15:00 - 16:00";
            case 7 -> "16:00 - 17:00";
            default -> null;
        };
    }

    public static TimeSlot fromStringToTimeSlot(String time){
        return switch (time){
            case "9:00 - 10:00" -> NINE_TEN;
            case "10:00 - 11:00" -> TEN_ELEVEN;
            case "11:00 - 12:00" -> ELEVEN_TWELVE;
            case "12:00 - 13:00" -> TWELVE_THIRTEEN;
            case "14:00 - 15:00" -> FOURTEEN_FIFTEEN;
            case "15:00 - 16:00" -> FIFTEEN_SIXTEEN;
            case "16:00 - 17:00" -> SIXTEEN_SEVENTEEN;
            default -> null;
        };
    }
}
