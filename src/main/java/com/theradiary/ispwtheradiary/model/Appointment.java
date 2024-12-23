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
    private boolean available;

    public Appointment(Psychologist psychologist, DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online) {
        this.psychologist = psychologist;
        this.day = day;
        this.timeSlot = timeSlot;
        this.inPerson = inPerson;
        this.online = online;
        this.patient = null;
        this.available = true;
    }

    //Costruttore usato per inviare la richiesta
    public Appointment(Psychologist psychologist, DayOfTheWeek day, TimeSlot timeSlot, Patient patient) {
        this.psychologist = psychologist;
        this.day = day;
        this.timeSlot = timeSlot;
        this.patient = patient;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
