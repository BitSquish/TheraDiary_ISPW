package com.theradiary.ispwtheradiary.dao.demo;

import com.theradiary.ispwtheradiary.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.dao.demo.shared.SharedResources;

import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;

import com.theradiary.ispwtheradiary.exceptions.DAOException;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.*;

import java.util.List;
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
            throw new DAOException();
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
        //Prendo la lista delle richieste inviate dallo psicologo
        List<Request> requests = SharedResources.getInstance().getRequestsSent().get(request.getPsychologist().getCredentials().getMail());
        //Rimuovo la richiesta dalla lista appoggio
        requests.removeIf(r -> r.getPatient().getCredentials().getMail().equals(request.getPatient().getCredentials().getMail()));
        //Elimino tutte le richieste di quello psicologo dalla lista
        SharedResources.getInstance().getRequestsSent().remove(request.getPsychologist().getCredentials().getMail());
        //Reinserisco le richieste (tranne quella eliminata)
        SharedResources.getInstance().getRequestsSent().put(request.getPsychologist().getCredentials().getMail(), requests);
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
            throw new DAOException();
        }
    }

    @Override
    public void registerMedicalOffice(MedicalOffice medicalOffice) {
        if (SharedResources.getInstance().getMedicalOffices().containsKey(medicalOffice.getPsychologist())) {
            throw new DAOException();
        }
        SharedResources.getInstance().getMedicalOffices().put(medicalOffice.getPsychologist(), medicalOffice);
    }



    @Override
    public void deleteMedicalOffice(MedicalOffice medicalOffice) {
        SharedResources.getInstance().getMedicalOffices().remove(medicalOffice.getPsychologist());
    }



}
