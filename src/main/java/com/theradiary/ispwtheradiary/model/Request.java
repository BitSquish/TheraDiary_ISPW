package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;

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

    //Costruttore usato nel metodo per verificare se un paziente ha già inviato una richiesta per un certo psicologo
    public Request(Patient patient, Psychologist psychologist) {
        this.patient = patient;
        this.psychologist = psychologist;
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

}
