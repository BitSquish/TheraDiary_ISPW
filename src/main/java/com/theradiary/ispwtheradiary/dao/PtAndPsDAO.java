package com.theradiary.ispwtheradiary.dao;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Request;

public interface PtAndPsDAO {
    //Metodi per la gestione delle richieste
    void sendRequest(Request request);
    boolean hasAlreadySentARequest(Request request);
    boolean hasAlreadyAPsychologist(Patient patient);


}
