package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.PsychologistDescriptionController;
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
        Printer.println("Vuoi inviare una richiesta a questo psicologo? (s/n)");
        String risposta = scanner.next();
        if(risposta.equals("s")){
            RequestBean requestBean = new RequestBean(user,selectedPsychologist, LocalDate.now());
            if(psychologistDescriptionController.hasAlreadySentARequest(user,selectedPsychologist)){
                Printer.println("Hai già inviato una richiesta a questo psicologo");
                goNext(context,new HomePatientCLI(user));
            }else if(psychologistDescriptionController.hasAlreadyAPsychologist(user)){
                Printer.println("Hai già un psicologo");
                goNext(context,new HomePatientCLI(user));
            }else{
                psychologistDescriptionController.sendRequest(requestBean);
                Printer.println("Richiesta inviata con successo");
                goNext(context,new HomePatientCLI(user));
            }
        } else {
            goNext(context,new HomePatientCLI(user));
        }
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
    }
}
