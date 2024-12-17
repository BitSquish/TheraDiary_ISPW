package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PtAndPsQuery {
    private PtAndPsQuery() {
    }
    public static void sendRequest(String psychologist, String patient, LocalDate date)  {
        String query = "INSERT INTO request (psychologist, patient, date) VALUES (?, ?, ?)";
        try(Connection connection = ConnectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, psychologist);
            preparedStatement.setString(2, patient);
            preparedStatement.setDate(3, Date.valueOf(date));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nell'invio della richiesta", e);
        }
    }
}
