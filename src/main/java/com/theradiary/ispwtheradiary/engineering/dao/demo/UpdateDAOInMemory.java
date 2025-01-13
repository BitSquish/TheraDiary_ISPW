package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateDAOInMemory implements UpdateDAO {
    private final Map<String, MedicalOffice> medicalOffices = new HashMap<>();
    private final Map<String, Psychologist> psychologists = new HashMap<>();
    private final Map<String, Patient> patients = new HashMap<>();
    private final Map<String, Appointment> appointments = new HashMap<>();
    @Override
    public boolean emailExists(String mail) {return psychologists.containsKey(mail) || patients.containsKey(mail);}
    @Override
    public void modifyMedicalOffice(MedicalOffice medicalOffice) {medicalOffices.put(medicalOffice.getPsychologist(), medicalOffice);}
    @Override
    public void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws MailAlreadyExistsException {
        if (!Objects.equals(newCredentials.getMail(), oldCredentials.getMail()) && emailExists(newCredentials.getMail())) {
            throw new MailAlreadyExistsException("Mail già registrata");
        }
        // Aggiornamento delle credenziali dell'utente
        if (psychologists.containsKey(oldCredentials.getMail())) {
            Psychologist psychologist = psychologists.remove(oldCredentials.getMail());
            psychologist.setCredentials(newCredentials);
            psychologists.put(newCredentials.getMail(), psychologist);
        } else if (patients.containsKey(oldCredentials.getMail())) {
            Patient patient = patients.remove(oldCredentials.getMail());
            patient.setCredentials(newCredentials);
            patients.put(newCredentials.getMail(), patient);
        } else {
            throw new PersistenceOperationException("Utente non trovato", new Throwable());
        }
    }

    @Override
    public void modifyPatient(Patient patient) { patients.put(patient.getCredentials().getMail(), patient); }
    @Override
    public void modifyPsychologist(Psychologist psychologist) { psychologists.put(psychologist.getCredentials().getMail(), psychologist); }
    @Override
    public void joinPagPsychologist(Psychologist psychologist) { psychologists.put(psychologist.getCredentials().getMail(), psychologist); }
    @Override
    public void joinPagPatient(Patient patient) { patients.put(patient.getCredentials().getMail(), patient); }
    @Override
    public void deleteRequest(Request request) {
        Printer.print("Richiesta eliminata per"+request.getPatient().getCredentials().getMail());
    }

    @Override
    public void setRequestForAppointment(Appointment appointment) {
        String key = appointment.getPsychologist().getCredentials().getMail() + "_" + appointment.getDay() + "_" + appointment.getTimeSlot();
        appointments.put(key, appointment);
    }

    @Override
    public void clearAppointments(Psychologist psychologist, DayOfTheWeek day) {appointments.entrySet().removeIf(entry -> entry.getKey().startsWith(psychologist.getCredentials().getMail()) && entry.getKey().contains(day.toString()));}

    @Override
    public void addAppointments(Appointment appointmentToAdd) {
        String key = appointmentToAdd.getPsychologist().getCredentials().getMail() + "_" + appointmentToAdd.getDay() + "_" + appointmentToAdd.getTimeSlot();
        appointments.put(key, appointmentToAdd);
    }

    @Override
    public void setPatientsPsychologist(Patient patient) {
        if (psychologists.containsKey(patient.getPsychologist().getCredentials().getMail())) {
            patient.setPsychologist(patient.getPsychologist());
            patients.put(patient.getCredentials().getMail(), patient);
        } else {
            throw new PersistenceOperationException("Psicologo non trovato", new Throwable());
        }
    }

    @Override
    public void registerMedicalOffice(MedicalOffice medicalOffice) {
        if (medicalOffices.containsKey(medicalOffice.getPsychologist())) {
            throw new PersistenceOperationException("Lo studio medico è già registrato", new Throwable());
        }
        medicalOffices.put(medicalOffice.getPsychologist(), medicalOffice);
    }

}
