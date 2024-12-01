package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.model.beans.RequestBean;
import java.time.LocalDate;

public class Request {
    private Patient patient;
    private Psychologist psychologist;
    private LocalDate date;

    public Request(Patient patient, Psychologist psychologist, LocalDate date) {
        this.patient = patient;
        this.psychologist = psychologist;
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Psychologist getPsychologist() {
        return psychologist;
    }
    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //converte da model a bean
    public RequestBean toBean(){
        return new RequestBean(patient.toBean(), psychologist.toBean(), date);
    }
}
