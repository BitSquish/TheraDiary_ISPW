package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UpdateDAO {
    private UpdateDAO(){}

    private static boolean emailExists(String mail) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()){
            int rs = LoginAndRegistrationQuery.checkMail(conn, mail);
            if (rs != 0)
                return true;
        }
        return false;
    }
    public static void modifyMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getPsychologist(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella modifica dello studio medico", e);
        }
    }

    public static void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws SQLException, MailAlreadyExistsException {
        try(Connection conn = ConnectionFactory.getConnection()){
            if(!Objects.equals(newCredentials.getMail(), oldCredentials.getMail()) && emailExists(newCredentials.getMail()))
                throw new MailAlreadyExistsException(("Mail già registrata"));
            UpdateQuery.modifyCredentials(conn, newCredentials.getMail(), newCredentials.getPassword(), oldCredentials.getMail());

        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella modifica delle credenziali", e);
        } catch (MailAlreadyExistsException e) {
            throw new MailAlreadyExistsException(e.getMessage());
        }
    }

    public static void modifyPsychologist(Psychologist psychologist) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPsychologist(conn, psychologist.getCredentials().getMail(), psychologist.getName(), psychologist.getSurname(), psychologist.getCity(), psychologist.getDescription(), psychologist.isInPerson(), psychologist.isOnline());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella modifica dello psicologo", e);
        }
    }

    public static void modifyPatient(Patient patient) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPatient(conn, patient.getCredentials().getMail(), patient.getName(), patient.getSurname(), patient.getCity(), patient.getDescription(), patient.isInPerson(), patient.isOnline());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella modifica del paziente", e);
        }
    }

    public static void joinPagPsychologist(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPsychologist(conn, psychologist.getCredentials().getMail());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nell'aggiunta del  pag", e);
        }
    }

    public static void joinPagPatient(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPatient(conn, patient.getCredentials().getMail());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nell'aggiunta del  pag", e);
        }
    }

    public static void deleteRequest(Request request) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.deleteRequest(conn, request.getPatient().getCredentials().getMail(), request.getPsychologist().getCredentials().getMail(), request.getDate());
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setPatientsPsychologist(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.setPatientsPsychologist(conn, patient.getCredentials().getMail(), patient.getPsychologist().getCredentials().getMail());
            UpdateQuery.deleteOtherRequests(conn, patient);
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public static void addAppointments(Appointment appointmentsToAdd) {
        String psychologist = appointmentsToAdd.getPsychologist().getCredentials().getMail();
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.addAppointment(conn, psychologist, appointmentsToAdd.getDay(), appointmentsToAdd.getTimeSlot(), appointmentsToAdd.isInPerson(), appointmentsToAdd.isOnline(), appointmentsToAdd.getPatient().getCredentials().getMail(), appointmentsToAdd.isAvailable());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella rimozione degli appuntamenti", e);
        }
    }

    public static void modifyAppointments(List<Appointment> appointmentsToModify) {
        String psychologist = appointmentsToModify.get(0).getPsychologist().getCredentials().getMail();
        try(Connection conn = ConnectionFactory.getConnection()){
            for(Appointment appointment : appointmentsToModify){
                UpdateQuery.modifyAppointment(conn, psychologist, appointment.getDay(), appointment.getTimeSlot(), appointment.isInPerson(), appointment.isOnline());
            }
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella modifica degli appuntamenti", e);
        }
    }

    public static void clearAppointments(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.clearAppointments(conn, psychologist.getCredentials().getMail());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella cancellazione degli appuntamenti", e);
        }
    }

    public static void setRequestForAppointment(Appointment appointment) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.setRequestForAppointment(conn, appointment.getPsychologist().getCredentials().getMail(), appointment.getDay(), appointment.getTimeSlot(), appointment.getPatient().getCredentials().getMail());
        } catch(SQLException e){
            throw new DatabaseOperationException("Errore nella richiesta di appuntamento", e);
        }
    }
}

