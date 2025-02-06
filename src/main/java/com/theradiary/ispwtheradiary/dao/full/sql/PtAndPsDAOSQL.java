package com.theradiary.ispwtheradiary.dao.full.sql;

import com.theradiary.ispwtheradiary.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.query.PtAndPsQuery;

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
        } catch (SQLException | DatabaseOperationException e){
            handleException(e);

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
        } catch (SQLException | DatabaseOperationException e) {
            handleException(e);
            return false;
        }
    }

   @Override
    public boolean hasAlreadyAPsychologist(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            return PtAndPsQuery.hasAlreadyAPsychologist(conn, patient.getCredentials().getMail());
        } catch (SQLException | DatabaseOperationException e) {
           handleException(e);
           return false;
        }
    }
    private void handleException(Exception e) {
        Printer.errorPrint(String.format("%s", e.getMessage()));
    }
}
