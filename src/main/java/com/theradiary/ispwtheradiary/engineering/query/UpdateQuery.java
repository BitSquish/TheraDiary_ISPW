package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public static void modifyMail(Connection conn, String newMail, String oldMail)  {
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
    }

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
}
