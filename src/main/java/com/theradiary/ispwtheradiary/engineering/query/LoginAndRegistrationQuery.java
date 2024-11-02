package com.theradiary.ispwtheradiary.engineering.query;

//NOTA: conviene usare Statement per query senza input, per query con input si usa PreparedStatement

import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.beans.PatientBean;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndRegistrationQuery {
    public static ResultSet logQuery(Connection conn, Credentials credentialsBean) throws SQLException {
        String query = "SELECT mail, password, role FROM users WHERE mail = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, credentialsBean.getMail());
        pstmt.setString(2, credentialsBean.getPassword());
        return pstmt.executeQuery();
    }



    //QUERY REGISTRAZIONE
    public static int registerUser(Connection conn, Credentials credentialsBean) throws SQLException{
        String query = "INSERT INTO users (mail, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, credentialsBean.getMail());
        pstmt.setString(2, credentialsBean.getPassword());
        pstmt.setString(3, credentialsBean.getRole().toString());
        return pstmt.executeUpdate(); //restituisce il numero di righe influenzate dalla query
    }

    public static void registerPsychologist(Connection conn, Psychologist psychologist) throws SQLException, MailAlreadyExistsException {
        String query = "INSERT INTO psychologist (mail, name, surname, city, description, inPerson, online, pag) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, psychologist.getCredentials().getMail());
        pstmt.setString(2, psychologist.getName());
        pstmt.setString(3, psychologist.getSurname());
        pstmt.setString(4, psychologist.getCity());
        pstmt.setString(5, psychologist.getDescription());
        pstmt.setBoolean(6, psychologist.isInPerson());
        pstmt.setBoolean(7, psychologist.isOnline());
        pstmt.setBoolean(8, psychologist.isPag());
        int rs = pstmt.executeUpdate();
        if(rs == 0){
            throw new MailAlreadyExistsException("Mail già esistente");
        }
    }

    public static void registerPatient(Connection conn, Patient patient) throws SQLException, MailAlreadyExistsException {
        String query = "INSERT INTO patient (mail, name, surname, city, description, inPerson, online, pag) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, patient.getCredentials().getMail());
        pstmt.setString(2, patient.getName());
        pstmt.setString(3, patient.getSurname());
        pstmt.setString(4, patient.getCity());
        pstmt.setString(5, patient.getDescription());
        pstmt.setBoolean(6, patient.isInPerson());
        pstmt.setBoolean(7, patient.isOnline());
        pstmt.setBoolean(8, patient.isPag());
        int rs = pstmt.executeUpdate();
        if(rs == 0){
            throw new MailAlreadyExistsException("Mail già esistente");
        }
    }

    public static void registerMedicalOffice(Connection conn, String mail, String city, String postCode, String address, String otherInfo) throws SQLException {
        String query = "INSERT INTO medicaloffice (mail, city, postCode, address, otherInfo) VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        pstmt.setString(2, city);
        pstmt.setString(3, postCode);
        pstmt.setString(4, address);
        pstmt.setString(5, otherInfo);
        pstmt.executeUpdate();
        //Possono esserci problemi da gestire?
    }

    public static int checkMail(Connection conn, String mail) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, mail);
        ResultSet result = pstmt.executeQuery();
        result.next();
        return result.getInt(1);
    }
    public static void joinPag(Connection conn,String mail) throws SQLException {
        String query = "UPDATE users SET isPag = TRUE WHERE email = ?";
        PreparedStatement statement = conn.prepareStatement(query); {
            statement.setString(1,mail);
            statement.executeUpdate();
        }
    }


}


