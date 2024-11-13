package com.theradiary.ispwtheradiary.engineering.enums;

public enum Category {
    ANXIETY_DISORDER(1),
    DEPRESSION(2),
    BIPOLAR_DISORDER(3),
    PTSD_AND_CPTSD(4),
    SCHIZOPHRENIA(5),
    EATING_DISORDERS(6),
    DISSOCIAL_DISORDER(7),
    NEURODEVELOPMENTAL_DISORDERS(8),
    OTHER(9);

    private final int id;

    //costruttore di Role
    Category(int id) {
        this.id = id;
    }

    //Converte un intero al ruolo corrispondente
    public static Category convertIntToCategory(int id) {
        for (Category type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public static String traslateCategory(int id){
        return switch (id) {
            case 1 -> "Disturbi d'ansia";
            case 2 -> "Depressione";
            case 3 -> "Disturbo bipolare";
            case 4 -> "PTSD e CPTSD";
            case 5 -> "Schizofrenia";
            case 6 -> "Disturbi alimentari";
            case 7 -> "Disturbi dissociativi";
            case 8 -> "Disturbi del neurosviluppo";
            case 9 -> "Altro";
            default -> null;
        };
    }

    public int getId() {
        return id;
    }
}
