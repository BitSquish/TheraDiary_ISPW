package com.theradiary.ispwtheradiary.model;

import java.util.ArrayList;
import java.util.List;

public class Psychologist extends LoggedUser {
    private ArrayList<Patient> patients;

    public Psychologist(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline, boolean isPag, ArrayList<Patient> patients) {
        super(credentials, name, surname, city, description, isInPerson, isOnline, isPag);
        this.patients = patients != null ? patients : new ArrayList<Patient>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient){
        this.patients.add(patient);
    }

    public void removePatient(Patient patient){
        this.patients.remove(patient);
    }
}

