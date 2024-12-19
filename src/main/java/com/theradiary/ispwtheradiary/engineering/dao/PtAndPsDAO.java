package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.query.PtAndPsQuery;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PtAndPsDAO {
    private PtAndPsDAO() {
    }
    public static void sendRequest(Request request) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PtAndPsQuery.sendRequest(conn, request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail(), request.getDate());
        } catch (Exception e){
            throw new DatabaseOperationException("Errore nell'invio della richiesta", e);
        }
    }

    public static boolean hasAlreadySentARequest(Request request) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            ResultSet rs = PtAndPsQuery.hasAlreadySentARequest(conn, request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail());
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero della richiesta", e);
        }
    }

    public static boolean hasAlreadyAPsychologist(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            return PtAndPsQuery.hasAlreadyAPsychologist(conn, patient.getCredentials().getMail());
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero del paziente", e);
        }
    }
}
