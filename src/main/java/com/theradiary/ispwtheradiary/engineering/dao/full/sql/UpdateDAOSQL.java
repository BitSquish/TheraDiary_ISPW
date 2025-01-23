package com.theradiary.ispwtheradiary.engineering.dao.full.sql;



import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.*;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class UpdateDAOSQL implements UpdateDAO {
    @Override
    public boolean emailExists(String mail) throws SQLException, DatabaseOperationException {
        try (Connection conn = ConnectionFactory.getConnection()){
            int rs = LoginAndRegistrationQuery.checkMail(conn, mail);
            if (rs != 0)
                return true;
        }
        return false;
    }
    @Override
    public void modifyMedicalOffice(MedicalOffice medicalOffice) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getPsychologist(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override

    public void registerMedicalOffice(MedicalOffice medicalOffice) {
        try(Connection conn = ConnectionFactory.getConnection()){
            LoginAndRegistrationQuery.registerMedicalOffice(conn, medicalOffice.getPsychologist(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws MailAlreadyExistsException {
        try(Connection conn = ConnectionFactory.getConnection()){
            if(!Objects.equals(newCredentials.getMail(), oldCredentials.getMail()) && emailExists(newCredentials.getMail()))
                throw new MailAlreadyExistsException(("Mail già registrata"));
            UpdateQuery.modifyCredentials(conn, newCredentials.getMail(), newCredentials.getPassword(), oldCredentials.getMail());

        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        } catch (MailAlreadyExistsException e) {
            throw new MailAlreadyExistsException(e.getMessage());
        }
    }
    @Override
    public void modifyPsychologist(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPsychologist(conn, psychologist);
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void modifyPatient(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPatient(conn, patient);
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void joinPagPsychologist(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPsychologist(conn, psychologist.getCredentials().getMail());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void joinPagPatient(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPatient(conn, patient.getCredentials().getMail());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void deleteRequest(Request request) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.deleteRequest(conn, request.getPatient().getCredentials().getMail(), request.getPsychologist().getCredentials().getMail(), request.getDate());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void setPatientsPsychologist(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.setPatientsPsychologist(conn, patient.getCredentials().getMail(), patient.getPsychologist().getCredentials().getMail());
            UpdateQuery.deleteOtherRequests(conn, patient);
        } catch(SQLException | DatabaseOperationException e) {
            handleException(e);
        }
    }

    @Override
    public void addAppointments(Appointment appointmentToAdd) {
        try(Connection conn = ConnectionFactory.getConnection()){
            if(appointmentToAdd.getPatient() == null)
                UpdateQuery.addAppointment(conn, appointmentToAdd, null);
            else
                UpdateQuery.addAppointment(conn, appointmentToAdd, appointmentToAdd.getPatient().getCredentials().getMail());
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }

    public void modifyAppointments(List<Appointment> appointmentsToModify) {
        String psychologist = appointmentsToModify.get(0).getPsychologist().getCredentials().getMail();
        try(Connection conn = ConnectionFactory.getConnection()){
            for(Appointment appointment : appointmentsToModify){
                UpdateQuery.modifyAppointment(conn, psychologist, appointment.getDay(), appointment.getTimeSlot(), appointment.isInPerson(), appointment.isOnline());
            }
        } catch(SQLException | DatabaseOperationException e){
            handleException(e);
        }
    }
    @Override
    public void clearAppointments(Psychologist psychologist, DayOfTheWeek day) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.clearAppointments(conn, psychologist.getCredentials().getMail(), day.toString());
        } catch(SQLException | DatabaseOperationException e){
           handleException(e);
        }
    }
    @Override
    public void setRequestForAppointment(Appointment appointment) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.setRequestForAppointment(conn, appointment);
        } catch(SQLException |DatabaseOperationException e){
            handleException(e);
        }
    }
    private void handleException(Exception e) {
        Printer.errorPrint(String.format("%s", e.getMessage()));
    }
}

