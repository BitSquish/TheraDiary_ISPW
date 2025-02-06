package com.theradiary.ispwtheradiary.beans;

import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;

import static com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek.fromDayToString;
import static com.theradiary.ispwtheradiary.engineering.enums.TimeSlot.translateTimeSlot;

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

    public AppointmentBean(PsychologistBean psychologistBean, DayOfTheWeek day, TimeSlot timeSlot, String patientBean) {
        this.psychologistBean = psychologistBean;
        this.day = day;
        this.timeSlot = timeSlot;
        this.patientBean = patientBean;
    }

    public AppointmentBean() {

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
    @Override
    public String toString() {
        return "Appuntamento:" +
                "|" + fromDayToString(day) +
                "|" + translateTimeSlot(timeSlot.getId()) +
                "|" + (inPerson ? "In presenza" : "") +
                "|"+ (online ? "Online" : "") +
                "|" + (available ? "Non prenotato" : "Prenotato") +
                "|" + getPatientBean()+ "|";
    }
}
