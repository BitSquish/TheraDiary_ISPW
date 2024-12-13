package com.theradiary.ispwtheradiary.model;

import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;

public class Appointment {
    private Psychologist psychologist;
    private DayOfTheWeek day;
    private TimeSlot timeSlot;
    private boolean inPerson;
    private boolean online;
    private Patient patient;

    public Appointment(Psychologist psychologist, DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online) {
        this.psychologist = psychologist;
        this.day = day;
        this.timeSlot = timeSlot;
        this.inPerson = inPerson;
        this.online = online;
        this.patient = null;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    public DayOfTheWeek getDay() {
        return day;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public boolean isInPerson() {
        return inPerson;
    }

    public boolean isOnline() {
        return online;
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }

    public void setDay(DayOfTheWeek day) {
        this.day = day;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public AppointmentBean toBean() {
        return new AppointmentBean(psychologist.toBean(), day, timeSlot, inPerson, online);
    }
}
