package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveQuery {

    private RetrieveQuery(){}

    public static ResultSet searchPsychologist(Connection conn, String name, String surname, String city, boolean inPerson, boolean online, boolean pag) throws SQLException {
        String query = "SELECT mail, name, surname, city, description, inPerson, online, pag  FROM psychologist WHERE 1=1";
        if(!name.isEmpty())
            query += " AND name = ?";
        if(!surname.isEmpty())
            query += " AND surname = ?";
        if(!city.isEmpty())
            query += " AND city = ?";
        if(inPerson)
            query += " AND inPerson = ?";
        if(online)
            query += " AND online = ?";
        if(pag)
            query += " AND pag = ?";
        try  {
            PreparedStatement pstmt = conn.prepareStatement(query);
            int i = 1;
            if (!name.isEmpty()) {
                pstmt.setString(i, name);
                i++;
            }
            if (!surname.isEmpty()) {
                pstmt.setString(i, surname);
                i++;
            }

            if (!city.isEmpty()) {
                pstmt.setString(i, city);
                i++;
            }
            if (inPerson) {
                pstmt.setBoolean(i, true);
                i++;
            }
            if (online) {
                pstmt.setBoolean(i, true);
                i++;
            }
            if (pag) {
                pstmt.setBoolean(i, true);
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            Printer.errorPrint("Errore nella ricerca dello psicologo");
            return null;
        }

    }

    public static ResultSet retrievePsychologist(Connection conn, String mail) throws SQLException {
        String query = "SELECT mail, name, surname, city, description, inPerson, online FROM psychologist WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static ResultSet retrievePatient(Connection conn, String mail) throws SQLException {
        String query = "SELECT mail, name, surname, city, description, inPerson, online, pag, psychologist FROM patient WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static ResultSet retrieveMedicalOffice(Connection conn, String mail) throws SQLException {
        String query = "SELECT mail, city, postCode, address, otherInfo FROM medicaloffice WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static ResultSet retrieveCategories(Connection conn, String mail) throws SQLException{
        String query = "SELECT category FROM category WHERE patient = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();
    }

    public static ResultSet retrieveMajors(Connection conn, String mail) throws SQLException{
        String query = "SELECT major FROM major WHERE psychologist = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        return pstmt.executeQuery();

    }

    public static ResultSet retrievePatientList(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT mail,name,surname,city,description,inPerson,online,pag FROM patient  WHERE psychologist = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero della lista pazienti", e);
        }

    }


    public static ResultSet checkPatientPag(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT pag FROM patient WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero del pag", e);
        }
    }

    public static ResultSet checkPsychologistPag(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT pag FROM psychologist WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero del pag", e);
        }
    }

    public static ResultSet retrieveRequests(Connection conn, String mailPsychologist) throws SQLException {
        //Mettere un order by date (da più a meno recente)
        String query = "SELECT patient.mail,patient.name, patient.surname, patient.city, patient.description, patient.inPerson, patient.online, patient.pag, request.date " +
                "FROM patient JOIN request ON patient.mail = request.patient WHERE request.psychologist = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mailPsychologist);
        return pstmt.executeQuery();
    }

    public static ResultSet retrieveSlotTime(Connection conn, String mailPsychologist, String dayOfTheWeek) throws SQLException {
        String query = "SELECT psychologist, day, timeSlot, inPerson, online, patient FROM appointment WHERE psychologist = ? and day = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mailPsychologist);
        pstmt.setString(2, dayOfTheWeek);
        return pstmt.executeQuery();
    }

    public static ResultSet yourPsychologist(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT psychologist.* FROM patient Join psychologist ON patient.psychologist = psychologist.mail WHERE patient.mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero del proprio psicologo", e);
        }
    }

    public static ResultSet retrieveAllAppointments(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT psychologist, day, timeSlot, inPerson, online, patient, available FROM appointment WHERE psychologist = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero degli appuntamenti", e);
        }
    }

    public static ResultSet retrieveAppointment(Connection conn, String psychologist, String day, String timeSlot) throws DatabaseOperationException {
        String query = "SELECT psychologist, day, timeSlot, inPerson, online, patient, available FROM appointment WHERE psychologist = ? AND day = ? AND timeSlot = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, psychologist);
            pstmt.setString(2, day);
            pstmt.setString(3, timeSlot);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero dell'appuntamento", e);
        }
    }

    public static ResultSet retrievePatientAppointment(Connection conn, String psychologist, String patient, boolean availability) throws DatabaseOperationException {
        String query = "SELECT day, timeSlot, inPerson, online FROM appointment WHERE psychologist = ? AND patient = ? AND available = ?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, psychologist);
            pstmt.setString(2, patient);
            pstmt.setBoolean(3, availability);
            return pstmt.executeQuery();
        } catch (SQLException e){
            throw new DatabaseOperationException("Errore nel recupero dell'appuntamento", e);
        }
    }
}
