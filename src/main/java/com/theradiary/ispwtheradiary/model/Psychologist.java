package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.model.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.model.beans.PsychologistBean;

import java.util.ArrayList;
import java.util.List;

public class Psychologist extends LoggedUser {
    private ArrayList<Patient> patients;
    private ArrayList<Major> majors;


    public Psychologist(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        super(credentials, name, surname, city, description, isInPerson, isOnline);
        this.setPag(false);
        this.patients = new ArrayList<>();
        this.majors = new ArrayList<>();
    }

    public Psychologist(Credentials credentials) {
        super(credentials);
        this.patients = new ArrayList<>();
        this.majors = new ArrayList<>();
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
    public void addMajor(Major major){
        if(major!=null)
            this.majors.add(major);
    }

    public void removeMajor(Major major){this.majors.remove(major);}

    public List<Major> getMajors() {return majors;
    }
    public void setMajors(List<Major> majors) {
        this.majors = (ArrayList<Major>) majors;
    }

    @Override
    public PsychologistBean toBean() {
        return new PsychologistBean(getCredentials().toBean(), getName(), getSurname(), getCity(), getDescription(), isInPerson(), isOnline());
    }


}

