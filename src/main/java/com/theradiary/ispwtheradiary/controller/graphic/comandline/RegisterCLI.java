package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.UserRegistrationController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.Validator;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.Scanner;

public class RegisterCLI extends AbstractState {
    Validator validator = new Validator();


    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineImpl contextSM) {
        //metodo effettivo per la registrazione

        Printer.print("   Nome: ");
        String nome = scanner.next();

        Printer.print("   Cognome: ");
        String cognome = scanner.next();
        Printer.print("Città:");
        String city = scanner.next();


        Printer.print("   Email: ");
        String email = scanner.next();

        try {
            validator.isValidMail(email, null);
        } catch (Exception e) {
            Printer.errorPrint("Email non valida");
            return;
        }

        Printer.print("   Password: ");
        String password = scanner.next();


        boolean sbagliato = true;
        boolean isPsychologist = false;

        while (sbagliato) {

            Printer.print("   Ruolo[psicolgo/paziente]: ");
            String ruolo = scanner.next();

            switch (ruolo) {
                case ("psicologo"):
                    isPsychologist = true;
                    sbagliato = false;
                    break;
                case ("paziente"):
                    sbagliato = false;
                    break;
                default:
                    Printer.errorPrint("Input invalido. Seleziona un'opzione valida.");
                    break;
            }
        }

        try {
            if (isPsychologist) {
                PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(email, password, Role.PSYCHOLOGIST), nome, cognome, city, null, false, false);
                UserRegistrationController.registerPsychologist(psychologistBean);
            } else {
                PatientBean patientBean = new PatientBean(new CredentialsBean(email, password, Role.PATIENT), nome, cognome, city, null, false, false);
                UserRegistrationController.registerPatient(patientBean);
            }
            Printer.println("---------------------------------------------------------");
            Printer.println("L'utente è stato correttamente registrato.");
        }catch(Exception e){
                Printer.errorPrint("Email già in uso.");
        }
    }
    /*Pattern state*/

    @Override
    public void enter(StateMachineImpl contextSM) {
        /* capire se è necessaria qualche inizializzazione per lo stato*/
        stampa();
    }

    @Override
    public void exit(StateMachineImpl contextSM) {
        //qui dobbiamo specificare azioni particolari relative all'uscita da questo stato
        Printer.println("Verrete reindirizzati alla Home");
    }

    @Override
    public void stampa() {
        Printer.println("-------------- Registrazione --------------");
        Printer.println("Inserite le informazioni necessarie per la registrazione.");
    }

    @Override
    public void showMenu() {
        Printer.println("1. Conferma"); //in questo caso facciamo il login in automatico
        Printer.println("2. Indietro");
        Printer.println("0. Esci");
        Printer.print("Opzione scelta: ");
    }
}





