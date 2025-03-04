package com.theradiary.ispwtheradiary.query;


import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.model.Appointment;
import com.theradiary.ispwtheradiary.model.LoggedUser;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateQuery {

    private UpdateQuery() {
    }
    public static void modifyMedicalOffice(Connection conn, String mail, String city, String postCode, String address, String otherInfo) throws SQLException, DatabaseOperationException {
        String query = "UPDATE medicaloffice SET city = ?, postCode = ?, address = ?, otherInfo = ? WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, city);
            pstmt.setString(2, postCode);
            pstmt.setString(3, address);
            pstmt.setString(4, otherInfo);
            pstmt.setString(5, mail);  // This is the parameter for the WHERE clause
            pstmt.executeUpdate();
        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella modifica dell'indirizzo", e);
        }//Possono esserci problemi da gestire?
    }

    public static void modifyPsychologist(Connection conn, Psychologist psychologist) throws SQLException, DatabaseOperationException {
        String query = "UPDATE psychologist SET name = ?, surname = ?, city = ?, description = ?, inPerson = ?, online = ? WHERE mail = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            setModifyParameters(pstmt, psychologist);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella modifica del profilo", e);
        }
    }

    private static void setModifyParameters(PreparedStatement pstmt, LoggedUser loggedUser) throws SQLException {
        pstmt.setString(1, loggedUser.getName());
        pstmt.setString(2, loggedUser.getSurname());
        pstmt.setString(3, loggedUser.getCity());
        pstmt.setString(4, loggedUser.getDescription());
        pstmt.setBoolean(5, loggedUser.isInPerson());
        pstmt.setBoolean(6, loggedUser.isOnline());
        pstmt.setString(7, loggedUser.getCredentials().getMail());
    }

    public static void modifyCredentials(Connection conn, String mail, String password, String oldMail) throws DatabaseOperationException {
        String query = "UPDATE users SET mail = ?, password = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.setString(2, password);
            pstmt.setString(3, oldMail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella modifica delle credenziali", e);
        }
    }


    public static void modifyPatient(Connection conn, Patient patient) throws DatabaseOperationException {
        String query = "UPDATE patient SET name = ?, surname = ?, city = ?, description = ?, inPerson = ?, online = ? WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            setModifyParameters(pstmt, patient);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella modifica del profilo", e);
        }
    }

    public static void joinPagPsychologist(Connection conn, String mail) throws DatabaseOperationException {
        String query = "UPDATE psychologist SET pag = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'aggiunta al pag", e);
        }
    }

    public static void joinPagPatient(Connection conn, String mail) throws DatabaseOperationException {
        String query = "UPDATE patient SET pag = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, true);
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'aggiunta al pag", e);
        }
    }

    public static void deleteRequest(Connection conn, String mailPatient, String mailPsychologist, LocalDate date) throws DatabaseOperationException {
        String query = "DELETE FROM request WHERE patient = ? AND psychologist = ? AND date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mailPatient);
            pstmt.setString(2, mailPsychologist);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella rimozione della richiesta", e);
        }
    }

    public static void setPatientsPsychologist(Connection conn, String patient, String psychologist) throws DatabaseOperationException {
        String query = "UPDATE patient SET psychologist = ? WHERE mail = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, psychologist);
            pstmt.setString(2, patient);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'associazione del paziente", e);
        }
    }

    public static void deleteOtherRequests(Connection conn, Patient patient) throws DatabaseOperationException {
        String query = "DELETE FROM request WHERE patient = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patient.getCredentials().getMail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella rimozione delle altre richieste", e);
        }
    }

    public static void addAppointment(Connection conn, Appointment appointment, String patient) throws DatabaseOperationException {
        String query = "INSERT INTO appointment VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, appointment.getPsychologist().getCredentials().getMail());
            pstmt.setString(2, appointment.getDay().toString());
            pstmt.setString(3, appointment.getTimeSlot().toString());
            pstmt.setBoolean(4, appointment.isInPerson());
            pstmt.setBoolean(5, appointment.isOnline());
            if(patient == null)
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            else
                pstmt.setString(6, patient);
            pstmt.setBoolean(7, appointment.isAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'aggiunta dell'appuntamento", e);
        }
    }



    public static void modifyAppointment(Connection conn, String psychologist, DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online) throws DatabaseOperationException {
        String query = "UPDATE appointment SET psychologist = ?, day = ?, timeSlot = ?, inPerson = ?, online = ? WHERE psychologist = ? AND day = ? AND timeSlot = ? AND inPerson = ? AND online = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, psychologist);
            pstmt.setString(2, day.toString());
            pstmt.setString(3, timeSlot.toString());
            pstmt.setBoolean(4, inPerson);
            pstmt.setBoolean(5, online);
            pstmt.setString(6, psychologist);
            pstmt.setString(7, day.toString());
            pstmt.setString(8, timeSlot.toString());
            pstmt.setBoolean(9, inPerson);
            pstmt.setBoolean(10, online);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella modifica dell'appuntamento", e);
        }
    }

    public static void clearAppointments(Connection conn, String mail, String day) throws DatabaseOperationException {
        String query = "DELETE FROM appointment WHERE psychologist = ? AND day = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, mail);
            pstmt.setString(2, day);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella rimozione degli appuntamenti", e);
        }
    }

    public static void setRequestForAppointment(Connection conn, Appointment appointment) throws DatabaseOperationException {
        String query = "UPDATE appointment SET patient = ?, inPerson = ?, online = ?, available = ? WHERE psychologist = ? AND day = ? AND timeSlot = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, appointment.getPatient().getCredentials().getMail());
            pstmt.setBoolean(2, appointment.isInPerson());
            pstmt.setBoolean(3, appointment.isOnline());
            pstmt.setBoolean(4, appointment.isAvailable());
            pstmt.setString(5, appointment.getPsychologist().getCredentials().getMail());
            pstmt.setString(6, appointment.getDay().toString());
            pstmt.setString(7, appointment.getTimeSlot().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella richiesta di appuntamento", e);
        }
    }

    public static void deletePsychologist(Connection conn, String mail) throws DatabaseOperationException {
        String query = "DELETE FROM psychologist WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'eliminazione dello psicologo", e);
        }
    }

    public static void deleteMedicalOffice(Connection conn, String psychologist) throws DatabaseOperationException {
        String query = "DELETE FROM medicaloffice WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, psychologist);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'eliminazione dello studio medico", e);
        }
    }

    public static void deleteUser(Connection conn, String mail) throws DatabaseOperationException {
        String query = "DELETE FROM users WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'eliminazione dell'utente", e);
        }
    }

    public static void deleteCategory(Connection conn, String mail, String string) throws DatabaseOperationException {
        String query = "DELETE FROM category WHERE mail = ? AND category = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            pstmt.setString(2, string);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'eliminazione della categoria", e);
        }
    }

    public static void deletePatient(Connection conn, String mail) throws DatabaseOperationException {
        String query = "DELETE FROM patient WHERE mail = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'eliminazione del paziente", e);
        }
    }
}
