package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.model.*;
import java.util.Objects;

public class UpdateDAOInMemory implements UpdateDAO {

    @Override
    public boolean emailExists(String mail) {
        return SharedResources.getInstance().getPsychologists().containsKey(mail) || SharedResources.getInstance().getPatients().containsKey(mail);
    }

    @Override
    public void modifyMedicalOffice(MedicalOffice medicalOffice) {
        SharedResources.getInstance().getMedicalOffices().put(medicalOffice.getPsychologist(), medicalOffice);
    }

    @Override
    public void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws MailAlreadyExistsException {
        if (!Objects.equals(newCredentials.getMail(), oldCredentials.getMail()) && emailExists(newCredentials.getMail())) {
            throw new MailAlreadyExistsException("Mail già registrata");
        }

        // Aggiornamento delle credenziali dell'utente
        if (SharedResources.getInstance().getPsychologists().containsKey(oldCredentials.getMail())) {
            Psychologist psychologist = SharedResources.getInstance().getPsychologists().remove(oldCredentials.getMail());
            psychologist.setCredentials(newCredentials);
            SharedResources.getInstance().getPsychologists().put(newCredentials.getMail(), psychologist);
        } else if (SharedResources.getInstance().getPatients().containsKey(oldCredentials.getMail())) {
            Patient patient = SharedResources.getInstance().getPatients().remove(oldCredentials.getMail());
            patient.setCredentials(newCredentials);
            SharedResources.getInstance().getPatients().put(newCredentials.getMail(), patient);
        } else {
            throw new PersistenceOperationException("Utente non trovato", new Throwable());
        }
    }

    @Override
    public void modifyPatient(Patient patient) {
        SharedResources.getInstance().getPatients().put(patient.getCredentials().getMail(), patient);
    }

    @Override
    public void modifyPsychologist(Psychologist psychologist) {
        SharedResources.getInstance().getPsychologists().put(psychologist.getCredentials().getMail(), psychologist);
    }

    @Override
    public void joinPagPsychologist(Psychologist psychologist) {
        SharedResources.getInstance().getPsychologists().put(psychologist.getCredentials().getMail(), psychologist);
    }

    @Override
    public void joinPagPatient(Patient patient) {
        SharedResources.getInstance().getPatients().put(patient.getCredentials().getMail(), patient);
    }

    @Override
    public void deleteRequest(Request request) {
        Printer.print("Richiesta eliminata per " + request.getPatient().getCredentials().getMail());
    }

    @Override
    public void setRequestForAppointment(Appointment appointment) {
        String key = appointment.getPsychologist().getCredentials().getMail() + "_" + appointment.getDay() + "_" + appointment.getTimeSlot();
        SharedResources.getInstance().getAppointments().put(key, appointment);
    }

    @Override
    public void clearAppointments(Psychologist psychologist, DayOfTheWeek day) {
        SharedResources.getInstance().getAppointments().entrySet().removeIf(entry ->
                entry.getKey().startsWith(psychologist.getCredentials().getMail()) && entry.getKey().contains(day.toString()));
    }

    @Override
    public void addAppointments(Appointment appointmentToAdd) {
        String key = appointmentToAdd.getPsychologist().getCredentials().getMail() + "_" + appointmentToAdd.getDay() + "_" + appointmentToAdd.getTimeSlot();
        SharedResources.getInstance().getAppointments().put(key, appointmentToAdd);
    }

    @Override
    public void setPatientsPsychologist(Patient patient) {
        if (SharedResources.getInstance().getPsychologists().containsKey(patient.getPsychologist().getCredentials().getMail())) {
            patient.setPsychologist(patient.getPsychologist());
            SharedResources.getInstance().getPatients().put(patient.getCredentials().getMail(), patient);
        } else {
            throw new PersistenceOperationException("Psicologo non trovato", new Throwable());
        }
    }

    @Override
    public void registerMedicalOffice(MedicalOffice medicalOffice) {
        if (SharedResources.getInstance().getMedicalOffices().containsKey(medicalOffice.getPsychologist())) {
            throw new PersistenceOperationException("Lo studio medico è già registrato", new Throwable());
        }
        SharedResources.getInstance().getMedicalOffices().put(medicalOffice.getPsychologist(), medicalOffice);
    }


}
