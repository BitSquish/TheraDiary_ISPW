package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.query.PtAndPsQuery;

import com.theradiary.ispwtheradiary.model.Request;


public class PtAndPsDAO {
    private PtAndPsDAO() {
    }
    public static void sendRequest(Request request) {
        try{
            PtAndPsQuery.sendRequest(request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail(), request.getDate());
        } catch (Exception e){
            throw new DatabaseOperationException("Errore nell'invio della richiesta", e);
        }
    }
}
