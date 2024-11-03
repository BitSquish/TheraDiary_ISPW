package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Major;

import java.util.ArrayList;
import java.util.List;

public class Psychologist extends LoggedUser {
    private ArrayList<Patient> patients;
    private ArrayList<Major> majors;


    public Psychologist(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline, boolean isPag, ArrayList<Patient> patients, ArrayList<Major> majors) {
        super(credentials, name, surname, city, description, isInPerson, isOnline, isPag);
        this.patients = patients != null ? patients : new ArrayList<Patient>();
        this.majors = majors != null ? majors : new ArrayList<Major>();

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
    public void addMajor(Major major){this.majors.add(major);}

    public void removeMajor(Major major){this.majors.remove(major);}
}

