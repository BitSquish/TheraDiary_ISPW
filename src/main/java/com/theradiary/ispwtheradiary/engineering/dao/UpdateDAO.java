package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateDAO {
    private UpdateDAO(){}
    public static void modifyMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getMail(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
    public static void modifyUsers(String mail,String newMail,String newPassword,String password) throws SQLException{

        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyUser(conn, mail, newMail, newPassword, password);
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
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
}

