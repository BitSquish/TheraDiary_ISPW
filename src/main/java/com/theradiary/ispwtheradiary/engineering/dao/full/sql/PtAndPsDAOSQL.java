package com.theradiary.ispwtheradiary.engineering.dao.full.sql;

import com.theradiary.ispwtheradiary.engineering.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.PtAndPsQuery;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PtAndPsDAOSQL implements PtAndPsDAO {
    @Override
    public void sendRequest(Request request) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            PtAndPsQuery.sendRequest(conn, request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail(), request.getDate());
        } catch (Exception e){
            throw new PersistenceOperationException("Errore nell'invio della richiesta", e);
        }
    }

   @Override
    public boolean hasAlreadySentARequest(Request request) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            ResultSet rs = PtAndPsQuery.hasAlreadySentARequest(conn, request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail());
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero della richiesta", e);
        }
    }

   @Override
    public boolean hasAlreadyAPsychologist(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            return PtAndPsQuery.hasAlreadyAPsychologist(conn, patient.getCredentials().getMail());
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nel recupero del paziente", e);
        }
    }
}
