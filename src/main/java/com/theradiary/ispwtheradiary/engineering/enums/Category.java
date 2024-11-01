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
    private Category(int id) {
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

    public int getId() {
        return id;
    }
}
