package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.PsychologistDescriptionController;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class PsychologistProfileCLI extends AbstractState {
    protected PsychologistBean selectedPsychologist;
    protected PsychologistDescriptionController psychologistDescriptionController = new PsychologistDescriptionController();
    Scanner scanner = new Scanner(System.in);
    protected PatientBean user;
    public PsychologistProfileCLI(PatientBean user,PsychologistBean selectedPsychologist) {
        this.selectedPsychologist = selectedPsychologist;
        this.user = user;
    }
    /*Azione per invio richiesta*/
    @Override
    public void action(StateMachineImpl context) {
        try {
            String answer = scanner.next().trim().toLowerCase();
            if (answer.equals("s")) {
                handleRequest(context);
            } else if (answer.equals("n")) {
                goNext(context, new HomePatientCLI(user));
            }else {
                Printer.errorPrint(SCELTA_NON_VALIDA);
                goNext(context, new HomePatientCLI(user));
            }
        }catch (Exception e) {
            Printer.errorPrint(e.getMessage());
        }
    }
    private void handleRequest(StateMachineImpl context) throws  NoResultException {
        if (psychologistDescriptionController.hasAlreadySentARequest(user,selectedPsychologist)){
            Printer.errorPrint("Hai già inviato una richiesta a questo psicologo");
        } else if (psychologistDescriptionController.hasAlreadyAPsychologist(user)) {
            Printer.errorPrint("Hai già un psicologo");
        } else {
            //crea e invia richiesta
            RequestBean requestBean = new RequestBean(user, selectedPsychologist, LocalDate.now());
            psychologistDescriptionController.sendRequest(requestBean);
            Printer.println("Richiesta inviata con successo");
        }
        goNext(context,new HomePatientCLI(user));
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void enter(StateMachineImpl context){stampa();}
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Profilo dello psicologo-------------------");
        Printer.println("Nome: " + selectedPsychologist.getFullName());
        Printer.println("Email: " + selectedPsychologist.getCredentialsBean().getMail());
        Printer.println("Città: " + selectedPsychologist.getCity());
        Printer.println("Descrizione: " + selectedPsychologist.getDescription());
        Printer.println("Visita in presenza: " + selectedPsychologist.getModality());
        Printer.println("Visita online: " + selectedPsychologist.getMajors());
        Printer.println("\nVuoi inviare una richiesta a questo psicologo? (s/n)");
    }
}
