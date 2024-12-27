package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.MedicalOfficeRegistrationController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.MedicalOfficeBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.Scanner;

public class MedicalOfficeCLI extends AbstractState {
    protected LoggedUserBean user;
    public MedicalOfficeCLI(LoggedUserBean user) {
        this.user =  user;
    }

    Scanner scanner = new Scanner(System.in);
    /*Input parametri per la registrazione*/
    @Override
    public void action(StateMachineImpl context) {
        Printer.println("Città:");
        String city = scanner.next();
        Printer.println("Indirizzo:");
        String address = scanner.next();
        Printer.println("C.A.P.:");
        String cap = scanner.next();
        Printer.println("Info:");
        String info = scanner.next();
        try {
            MedicalOfficeBean medicalOfficeBean = new MedicalOfficeBean(user.getCredentialsBean().getMail(), city, address, cap, info);
            MedicalOfficeRegistrationController medicalOfficeRegistrationController = new MedicalOfficeRegistrationController();
            if(medicalOfficeRegistrationController.retrieveMedicalOffice(medicalOfficeBean)){
                Printer.println("Studio medico già registrato, vuoi modificarlo? (s/n)");
                String answer = scanner.next();
                if(answer.equals("s")){
                    medicalOfficeRegistrationController.modify(medicalOfficeBean);
                    Printer.printlnBlue("Studio medico modificato con successo");
                    goNext(context, new HomePsychologistCLI(user));
                } else {
                    Printer.println("Studio medico non modificato");
                    goNext(context, new HomePsychologistCLI(user));
                }
            } else {
                medicalOfficeRegistrationController.register(medicalOfficeBean);
                Printer.printlnBlue("Studio medico registrato con successo");
                goNext(context, new HomePsychologistCLI(user));
            }
        } catch (Exception e) {
            Printer.errorPrint("Errore nell'inserimento dei dati");
            action(context);
        }
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void enter(StateMachineImpl context){stampa();}
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Registrazione studio medico-------------------");
        Printer.println("Inserisci i dati richiesti:");
    }


}
