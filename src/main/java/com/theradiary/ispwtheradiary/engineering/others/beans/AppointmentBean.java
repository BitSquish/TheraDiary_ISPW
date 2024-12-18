package com.theradiary.ispwtheradiary.engineering.others.beans;

import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;

public class AppointmentBean {
    private PsychologistBean psychologistBean;
    private DayOfTheWeek day;
    private TimeSlot timeSlot;
    private boolean inPerson;
    private boolean online;
    private String patientBean;
    private boolean available;

    public AppointmentBean(PsychologistBean psychologistBean, DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online) {
        this.psychologistBean = psychologistBean;
        this.day = day;
        this.timeSlot = timeSlot;
        this.inPerson = inPerson;
        this.online = online;
        this.patientBean = null;
        this.available = true;
    }

    //Costruttore usato per inviare la richiesta
    public AppointmentBean(PsychologistBean psychologistBean, DayOfTheWeek day, TimeSlot timeSlot, String patientBean) {
        this.psychologistBean = psychologistBean;
        this.day = day;
        this.timeSlot = timeSlot;
        this.patientBean = patientBean;
    }


    public PsychologistBean getPsychologistBean() {
        return psychologistBean;
    }

    public DayOfTheWeek getDay() {
        return day;
    }

    public String getPatientBean() {
        return patientBean;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public boolean isOnline() {
        return online;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setPsychologistBean(PsychologistBean psychologistBean) {
        this.psychologistBean = psychologistBean;
    }

    public void setDay(DayOfTheWeek day) {
        this.day = day;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setPatientBean(String patientBean) {
        this.patientBean = patientBean;
    }
}
