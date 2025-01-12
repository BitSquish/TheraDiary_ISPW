package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Request;

public interface PtAndPsDAO {
    void sendRequest(Request request);
    boolean hasAlreadySentARequest(Request request);
    boolean hasAlreadyAPsychologist(Patient patient);


}
