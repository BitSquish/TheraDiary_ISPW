package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;

import java.sql.*;
import java.time.LocalDate;

public class PtAndPsQuery {
    private PtAndPsQuery() {
    }
    public static void sendRequest(Connection conn, String psychologist, String patient, LocalDate date) throws DatabaseOperationException {
        String query = "INSERT INTO request (psychologist, patient, date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, psychologist);
            preparedStatement.setString(2, patient);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'invio della richiesta", e);
        }
    }

    public static ResultSet hasAlreadySentARequest(Connection conn, String psychologist, String patient) throws DatabaseOperationException {
        String query = "SELECT COUNT(*) FROM request WHERE psychologist = ? AND patient = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, psychologist);
            preparedStatement.setString(2, patient);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel controllo della richiesta", e);
        }
    }

    public static boolean hasAlreadyAPsychologist(Connection conn, String mail) throws DatabaseOperationException {
        String query = "SELECT psychologist FROM patient WHERE mail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, mail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String psychologist = rs.getString("psychologist");
                    return psychologist != null && !psychologist.isEmpty();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel controllo dello psicologo", e);
        }

        return false;
    }
}
