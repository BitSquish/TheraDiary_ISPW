package com.theradiary.ispwtheradiary.engineering.query;


import com.theradiary.ispwtheradiary.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateQuery {
    public static void modifyMedicalOffice(Connection conn, String mail, String city, String postCode, String address, String otherInfo) throws SQLException {
        String query = "UPDATE medicaloffice SET city = ?, postCode = ?, address = ?, otherInfo = ? WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setString(2, postCode);
            pstmt.setString(3, address);
            pstmt.setString(4, otherInfo);
            pstmt.setString(5, mail);  // This is the parameter for the WHERE clause
            pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            throw new RuntimeException(e);
        }//Possono esserci problemi da gestire?
    }

    public static void modifyPsychologist(Connection conn, String mail, String name, String surname, String city, String description, boolean inPerson, boolean online) throws SQLException {
        System.out.println(mail); //Null
        String query = "UPDATE psychologist SET name = ?, surname = ?, city = ?, description = ?, inPerson = ?, online = ? WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, city);
            pstmt.setString(4, description);
            pstmt.setBoolean(5, inPerson);
            pstmt.setBoolean(6, online);
            pstmt.setString(7, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void modifyCredentials(Connection conn, String mail, String password, String oldMail) {
        String query = "UPDATE users SET mail = ?, password = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.setString(2, password);
            pstmt.setString(3, oldMail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        /*public static void modifyMail(Connection conn, String newMail, String oldMail)  {
        String query = "UPDATE users SET mail = ? WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,newMail);
            pstmt.setString(2,oldMail);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }
    public static void modifyPassword(Connection conn,String newPassword, String oldPassword){
        String query="UPDATE users SET password = ? WHERE password = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,newPassword);
            pstmt.setString(2,oldPassword);
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }*/

    public static void modifyPatient(Connection conn, String mail, String name, String surname, String city, String description, boolean inPerson, boolean online) {
        String query = "UPDATE patient SET name = ?, surname = ?, city = ?, description = ?, inPerson = ?, online = ? WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, city);
            pstmt.setString(4, description);
            pstmt.setBoolean(5, inPerson);
            pstmt.setBoolean(6, online);
            pstmt.setString(7, mail);  // This is the parameter for the WHERE clause
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void joinPagPsychologist(Connection conn, String mail) {
        String query = "UPDATE psychologist SET pag = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void joinPagPatient(Connection conn, String mail) {
        String query = "UPDATE patient SET pag = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void deleteRequest(Connection conn, String mailPatient, String mailPsychologist, LocalDate date) {
        String query = "DELETE FROM request WHERE patient = ? AND psychologist = ? AND date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mailPatient);
            pstmt.setString(2, mailPsychologist);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setPatientsPsychologist(Connection conn, String patient, String psychologist) {
        String query = "UPDATE patient SET psychologist = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, psychologist);
            pstmt.setString(2, patient);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void deleteOtherRequests(Connection conn, Patient patient) {
        String query = "DELETE FROM request WHERE patient = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patient.getCredentials().getMail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
