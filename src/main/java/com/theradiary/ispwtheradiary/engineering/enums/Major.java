package com.theradiary.ispwtheradiary.engineering.enums;

public enum Major {
    PSYCHOTHERAPY(1),
    CHILD_PSYCHOLOGY(2),
    HEALTH_PSYCHOLOGY(3),
    CLINICAL_NEUROPSYCHOLOGY(4),
    TRAUMA_PSYCHOLOGY(5),
    GERIATRIC_PSYCHOLOGY(6),
    FORENSIC_PSYCHOLOGY(7),
    ADDICTION_PSYCHOLOGY(8),
    OTHER(9);
    private final int id;
    Major(int id) {
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

    public static String translateMajor(int id){
        return switch (id) {
            case 1 -> "Psicoterapia";
            case 2 -> "Psicologia infantile";
            case 3 -> "Psicologia della salute";
            case 4 -> "Neuropsicologia clinica";
            case 5 -> "Psicologia del trauma";
            case 6 -> "Psicologia geriatrica";
            case 7 -> "Psicologia forense";
            case 8 -> "Psicologia delle dipendenze";
            case 9 -> "Altro";
            default -> null;
        };
    }
    public int getId() {
        return id;
    }
}
