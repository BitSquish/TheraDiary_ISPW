package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.*;
import com.theradiary.ispwtheradiary.model.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
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
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getMail(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void modifyCredentials(Credentials newCredentials, Credentials oldCredentials) throws SQLException, MailAlreadyExistsException {
        try(Connection conn = ConnectionFactory.getConnection()){
            if(!Objects.equals(newCredentials.getMail(), oldCredentials.getMail()) && emailExists(newCredentials.getMail()))
                throw new MailAlreadyExistsException(("Mail già registrata"));
            UpdateQuery.modifyCredentials(conn, newCredentials.getMail(), newCredentials.getPassword(), oldCredentials.getMail());

        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        } catch (MailAlreadyExistsException e) {
            throw new MailAlreadyExistsException(e.getMessage());
        }
    }
    /*public static void modifyMail(String newMail, String oldMail) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMail(conn, newMail, oldMail);
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
    public static void modifyPassword(String newPassword, String oldPassword) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPassword(conn, newPassword, oldPassword);
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }*/
    public static void modifyPsychologist(Psychologist psychologist) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPsychologist(conn, psychologist.getCredentials().getMail(), psychologist.getName(), psychologist.getSurname(), psychologist.getCity(), psychologist.getDescription(), psychologist.isInPerson(), psychologist.isOnline());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void modifyPatient(Patient patient) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPatient(conn, patient.getCredentials().getMail(), patient.getName(), patient.getSurname(), patient.getCity(), patient.getDescription(), patient.isInPerson(), patient.isOnline());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void joinPagPsychologist(Psychologist psychologist) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPsychologist(conn, psychologist.getCredentials().getMail());
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void joinPagPatient(Patient patient) {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.joinPagPatient(conn, patient.getCredentials().getMail());
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
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
        } catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

