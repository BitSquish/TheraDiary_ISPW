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
    public int getId() {
        return id;
    }
}
