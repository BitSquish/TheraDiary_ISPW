package com.theradiary.ispwtheradiary.beans;


import com.theradiary.ispwtheradiary.engineering.enums.Major;

import java.util.ArrayList;
import java.util.List;

public class PsychologistBean extends LoggedUserBean {
    private ArrayList<PatientBean> patientsBean;
    private ArrayList<Major> majors;

    public PsychologistBean(CredentialsBean credentialsBean, String name, String surname, String city, String description, boolean inPerson, boolean online) {
        super(credentialsBean, name, surname, city, description, inPerson, online);
        this.setPag(false);
        this.patientsBean = new ArrayList<>();
        this.majors = new ArrayList<>();
    }

    public PsychologistBean(CredentialsBean credentialsBean) {
        super(credentialsBean);
        this.patientsBean = new ArrayList<>();
        this.majors = new ArrayList<>();
    }

    public List<PatientBean> getPatientsBean() {
        return patientsBean;
    }

    public List<Major> getMajors() {
        return majors;
    }


    public void addMajor(Major major) {
        majors.add(major);
    }

    public void removeMajor(Major major) {
        this.majors.remove(major);
    }

    public void setMajor(List<Major> majors) {
        this.majors = (ArrayList<Major>) majors;
    }

}



