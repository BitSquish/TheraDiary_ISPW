package com.theradiary.ispwtheradiary.engineering.query;

//NOTA: conviene usare Statement per query senza input, per query con input si usa PreparedStatement

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndRegistrationQuery {
    private static final String ERROR="Errore nella registrazione";
    private LoginAndRegistrationQuery() {
    }
    public static ResultSet logQuery(Connection conn, Credentials credentialsBean) throws SQLException {
        String query = "SELECT mail, password, role FROM users WHERE mail = ? AND password = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, credentialsBean.getMail());
        pstmt.setString(2, credentialsBean.getPassword());
        return pstmt.executeQuery();
    }



    //QUERY REGISTRAZIONE
    public static int registerUser(Connection conn, Credentials credentialsBean) throws SQLException, DatabaseOperationException {
        String query = "INSERT INTO users (mail, password, role) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, credentialsBean.getMail());
            pstmt.setString(2, credentialsBean.getPassword());
            pstmt.setString(3, credentialsBean.getRole().toString());
            return pstmt.executeUpdate(); //restituisce il numero di righe influenzate dalla query
        }catch (SQLException e) {
            throw new DatabaseOperationException(ERROR, e);
        }
    }

    public static void registerPsychologist(Connection conn, Psychologist psychologist) throws SQLException, MailAlreadyExistsException, DatabaseOperationException {
        String query = "INSERT INTO psychologist (mail, name, surname, city, description, inPerson, online, pag) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, psychologist.getCredentials().getMail());
            pstmt.setString(2, psychologist.getName());
            pstmt.setString(3, psychologist.getSurname());
            pstmt.setString(4, psychologist.getCity());
            pstmt.setString(5, psychologist.getDescription());
            pstmt.setBoolean(6, psychologist.isInPerson());
            pstmt.setBoolean(7, psychologist.isOnline());
            pstmt.setBoolean(8, psychologist.isPag());
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                throw new MailAlreadyExistsException("Mail già esistente");
            }
        }catch (SQLException e) {
            throw new DatabaseOperationException(ERROR, e);
        }
    }

    public static void registerPatient(Connection conn, Patient patient) throws SQLException, MailAlreadyExistsException, DatabaseOperationException {
        String query = "INSERT INTO patient (mail, name, surname, city, description, inPerson, online, pag) VALUES (?,?,?,?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patient.getCredentials().getMail());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getSurname());
            pstmt.setString(4, patient.getCity());
            pstmt.setString(5, patient.getDescription());
            pstmt.setBoolean(6, patient.isInPerson());
            pstmt.setBoolean(7, patient.isOnline());
            pstmt.setBoolean(8, patient.isPag());
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                throw new MailAlreadyExistsException("Mail già esistente");
            }
        }catch (SQLException e) {
            throw new DatabaseOperationException(ERROR, e);
        }
    }

    public static void registerMedicalOffice(Connection conn, String mail, String city, String postCode, String address, String otherInfo) throws SQLException, DatabaseOperationException {
        String query = "INSERT INTO medicaloffice (mail, city, postCode, address, otherInfo) VALUES (?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.setString(2, city);
            pstmt.setString(3, postCode);
            pstmt.setString(4, address);
            pstmt.setString(5, otherInfo);
            pstmt.executeUpdate();
            //Possono esserci problemi da gestire?
        }catch (SQLException e) {
            throw new DatabaseOperationException(ERROR, e);
        }
    }

    public static int checkMail(Connection conn, String mail) throws SQLException, DatabaseOperationException {
        String query = "SELECT COUNT(*) FROM users WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            ResultSet result = pstmt.executeQuery();
            result.next();
            return result.getInt(1);
        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella verifica della mail", e);
        }
    }
    public static void joinPag(Connection conn,String mail) throws  DatabaseOperationException {
        String query = "UPDATE users SET isPag = TRUE WHERE email = ?";
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1,mail);
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'aggiornamento dell'account", e);
        }
    }


    public static void removePatient(Connection conn, String mail) throws DatabaseOperationException {
        String query = "DELETE FROM patient WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella rimozione del paziente", e);
        }
    }
}


