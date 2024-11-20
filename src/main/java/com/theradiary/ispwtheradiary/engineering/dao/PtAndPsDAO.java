package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.query.PtAndPsQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;

import java.time.LocalDate;

public class PtAndPsDAO {
    private PtAndPsDAO() {
    }
    public static void sendRequest(Request request) {
        try{
            PtAndPsQuery.sendRequest(request.getPsychologist().getCredentials().getMail(), request.getPatient().getCredentials().getMail(), request.getDate());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage()); //GESTISCI ECCEZIONE IN MANIERA PIU' SPECIFICA
        }
    }
}
