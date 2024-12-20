
package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.LoginController;
import com.theradiary.ispwtheradiary.controller.application.UserRegistrationController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.Validator;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RegisterCLI extends AbstractState {



    private final Scanner scanner = new Scanner(System.in);
    private final LoginController login = new LoginController();


    @Override
    public void action(StateMachineImpl contextSM) {
        //metodo effettivo per la registrazione
        try {
            //Chiedo informazioni
            String nome=prompt("Nome: ");
            String surname=prompt("Cognome: ");
            String city=prompt("Città: ");
            String email=prompt("Email: ");
            if (!Validator.isValidMail(email,null)) {
                Printer.errorPrint("Email non valida");
                return;
            }
            String password=prompt("Password: ");
            boolean[] visitModes=getVisitModes();
            if(visitModes==null) return; //se la scelta non è valida
            String description=prompt("Descrizione: ");
            String role=prompt("Ruolo (psicologo/paziente): ").toLowerCase();
            //Creazione oggetti e registrazione utente
            switch (role){
                case "psicologo":
                    PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(email,password,Role.PSYCHOLOGIST),nome,surname,city,description,visitModes[0],visitModes[1]);
                    UserRegistrationController.registerPsychologist(psychologistBean);
                    Printer.println("Registrazione avvenuta con successo");
                    login.log(psychologistBean.getCredentialsBean());
                    goNext(contextSM,new HomePsychologistCLI(psychologistBean));
                    break;
                case "paziente":
                    PatientBean patientBean = new PatientBean(new CredentialsBean(email,password,Role.PATIENT),nome,surname,city,description,visitModes[0],visitModes[1]);
                    UserRegistrationController.registerPatient(patientBean);
                    Printer.println("Registrazione avvenuta con successo");
                    login.log(patientBean.getCredentialsBean());
                    goNext(contextSM,new HomePatientCLI(patientBean));
                    break;
                default:
                    Printer.errorPrint("Ruolo non valido. Scegli 'psiologo' o 'paziente'");
            }
        } catch (MailAlreadyExistsException e) {
            Printer.errorPrint("Errore: " + e.getMessage());
        } catch (Exception e) {
            Printer.errorPrint("Errore durante la registrazione. Riprova più tardi.");
        }
    }
    //metodo per chiedere l'input
    private String prompt(String message) {
        Printer.print(message+":");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            Printer.errorPrint("Inserire un valore valido");
            return prompt(message);
        }
        return input;
    }
    //metodo per chiedere le modalità di visita
    private boolean[] getVisitModes() {
        Printer.println("Modalità di visita:");
        Printer.println("1. In presenza");
        Printer.println("2. Online");
        Printer.println("3. Entrambe");
        Printer.print("Scelta: ");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    return new boolean[]{true, false};
                case 2:
                    return new boolean[]{false, true};
                case 3:
                    return new boolean[]{true, true};
                default:
                    Printer.errorPrint("Scelta non valida");
                    return new boolean[0];
            }
        } catch (InputMismatchException e) {
            Printer.errorPrint("Input non valido. Inserisci un numero tra 1 e 3");
            return new boolean[0];
        }

    }




    /*Pattern state*/

    @Override
    public void enter(StateMachineImpl contextSM) {
        stampa();
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
