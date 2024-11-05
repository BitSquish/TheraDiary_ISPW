package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateDAO {
    public static void modifyMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getMail(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void modifyPsychologist(Psychologist psychologist) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPsychologist(conn, psychologist.getCredentials().getMail(), psychologist.getName(), psychologist.getSurname(), psychologist.getCity(), psychologist.getDescription(), psychologist.isInPerson(), psychologist.isOnline(), psychologist.isPag());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void modifyPatient(Patient patient) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyPatient(conn, patient.getCredentials().getMail(), patient.getName(), patient.getSurname(), patient.getCity(), patient.getDescription(), patient.isInPerson(), patient.isOnline(), patient.isPag());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}

