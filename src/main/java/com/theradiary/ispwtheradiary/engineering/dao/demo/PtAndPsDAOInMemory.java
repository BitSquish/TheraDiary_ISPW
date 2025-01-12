package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Request;

public class PtAndPsDAOInMemory implements PtAndPsDAO {
    @Override
    public void sendRequest(Request request) {

    }

    @Override
    public boolean hasAlreadySentARequest(Request request) {
        return false;
    }

    @Override
    public boolean hasAlreadyAPsychologist(Patient patient) {
        return false;
    }
    //TODO
}
