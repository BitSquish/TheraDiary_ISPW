package com.theradiary.ispwtheradiary.engineering.enums;

public enum TimeSlot {
    NINE_TEN("9:00 - 10:00"),
    TEN_ELEVEN("10:00 - 11:00"),
    ELEVEN_TWELVE("11:00 - 12:00"),
    TWELVE_THIRTEEN("12:00 - 13:00"),
    FOURTEEN_FIFTEEN("14:00 - 15:00"),
    FIFTEEN_SIXTEEN("15:00 - 16:00"),
    SIXTEEN_SEVENTEEN("16:00 - 17:00");

    private String slot;

    public String getSlot() {
        return slot;
    }

    TimeSlot(String slot) {
        this.slot = slot;
    }
}
