package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.Major;


import java.util.ArrayList;
import java.util.List;

public class Psychologist extends LoggedUser {

    private ArrayList<Major> majors;


    public Psychologist(Credentials credentials, String name, String surname, String city, String description, boolean isInPerson, boolean isOnline) {
        super(credentials, name, surname, city, description, isInPerson, isOnline);
        this.setPag(false);
        this.majors = new ArrayList<>();
    }

    public Psychologist(Credentials credentials) {
        super(credentials);
        this.majors = new ArrayList<>();
    }



    public List<Major> getMajors() {return majors;
    }
    public void setMajors(List<Major> majors) {
        this.majors = (ArrayList<Major>) majors;
    }



}

