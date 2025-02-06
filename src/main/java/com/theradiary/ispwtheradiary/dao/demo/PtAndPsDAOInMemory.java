package com.theradiary.ispwtheradiary.dao.demo;

import com.theradiary.ispwtheradiary.dao.PtAndPsDAO;
import com.theradiary.ispwtheradiary.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;
import com.theradiary.ispwtheradiary.model.Request;

import java.util.ArrayList;

public class PtAndPsDAOInMemory implements PtAndPsDAO {
    // Mappa per tenere traccia delle richieste inviate (key: psicologo, value: lista di pazienti)

    @Override
    public void sendRequest(Request request) {
        // Aggiungi la richiesta alla mappa delle richieste inviate
        String psychologistMail = request.getPsychologist().getCredentials().getMail();

        // Usa computeIfAbsent per evitare il controllo esplicito e creazione della lista
        SharedResources.getInstance().getRequestsSent()
                .computeIfAbsent(psychologistMail, k -> new ArrayList<>()).add(request);
        Printer.println("Richiesta inviata da " + request.getPatient().getCredentials().getMail() + " a " + psychologistMail);
    }

    @Override
    public boolean hasAlreadySentARequest(Request request) {
        String psychologistMail = request.getPsychologist().getCredentials().getMail();
        String patientMail = request.getPatient().getCredentials().getMail();

        // Verifica se esiste già una richiesta per la coppia paziente-psicologo
        if (SharedResources.getInstance().getRequestsSent().containsKey(psychologistMail)) {
            for (Request existingRequest : SharedResources.getInstance().getRequestsSent().get(psychologistMail)) {
                if (existingRequest.getPatient().getCredentials().getMail().equals(patientMail)) {
                    return true;  // Se la richiesta esiste già, restituisce true
                }
            }
        }
        return false;  // Altrimenti, non è stata inviata una richiesta
    }

    @Override
    public boolean hasAlreadyAPsychologist(Patient patient) {
        String patientMail = patient.getCredentials().getMail();
        return SharedResources.getInstance().getPatientsWithPsychologists().containsKey(patientMail);
    }

    // Metodo per associare un paziente a un psicologo
    public void associatePatientWithPsychologist(Patient patient, Psychologist psychologist) {
        SharedResources.getInstance().getPatientsWithPsychologists()
                .put(patient.getCredentials().getMail(), psychologist);
    }


}
