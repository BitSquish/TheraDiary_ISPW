package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.query.PtAndPsQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.time.LocalDate;

public class PtAndPsDAO {
    private PtAndPsDAO() {
    }
    public static void sendRequest(Psychologist psychologist, Patient patient, LocalDate date) {
        try{
            PtAndPsQuery.sendRequest(psychologist.getCredentials().getMail(), patient.getCredentials().getMail(), date);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage()); //GESTISCI ECCEZIONE IN MANIERA PIU' SPECIFICA
        }
    }
}
