package com.theradiary.ispwtheradiary.engineering.enums;

public enum Major {
    PSICOTERAPIA(1),
    PSICOLOGIA_INFANTILE(2),
    PSICOLOGIA_DELLA_SALUTE(3),
    NEUROPSICOLOGIA_CLINICA(4),
    PSICOLOGIA_DEL_TRAUMA(5),
    PSICOLOGIA_GERIATRICA(6),
    PSICOLOGIA_FORENSE(7),
    PSICOLOGIA_DELLE_DIPENDENZE(8),
    OTHER(9);
    private final int id;
    private Major(int id) {
        this.id = id;
    }
    public static Major convertIntToMajor(int id) {
        for (Major type : values()) {
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
