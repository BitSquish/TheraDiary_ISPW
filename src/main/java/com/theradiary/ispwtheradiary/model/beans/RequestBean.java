package com.theradiary.ispwtheradiary.model.beans;

import java.time.LocalDate;

public class RequestBean {
    private PatientBean patientBean;
    private PsychologistBean psychologistBean;
    private LocalDate date;

    public RequestBean(PatientBean patientBean, PsychologistBean psychologistBean, LocalDate date) {
        this.patientBean = patientBean;
        this.psychologistBean = psychologistBean;
        this.date = date;
    }

    public PatientBean getPatientBean() {
        return patientBean;
    }
    public void setPatientBean(PatientBean patientBean) {
        this.patientBean = patientBean;
    }
    public PsychologistBean getPsychologistBean() {
        return psychologistBean;
    }
    public void setPsychologistBean(PsychologistBean psychologistBean) {
        this.psychologistBean = psychologistBean;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
