package com.theradiary.ispwtheradiary.controller.graphic.comandline;

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

import java.util.Scanner;

public class RegisterCLI extends AbstractState {



    private final Scanner scanner = new Scanner(System.in);


    @Override
    public void action(StateMachineImpl contextSM) {
        //metodo effettivo per la registrazione
        try {
            //Chiedo informazioni
            Printer.println("Inserite le informazioni necessarie per la registrazione.");

            Printer.print("Nome: ");
            String nome = scanner.nextLine();

            Printer.print("Cognome: ");
            String surname = scanner.nextLine();

            Printer.print("Città:");
            String city = scanner.nextLine();


            Printer.print("Email: ");
            String email = scanner.nextLine();

            if (!Validator.isValidMail(email,null)) {
                Printer.errorPrint("Email non valida");
                return;
            }

            Printer.print("Password: ");
            String password = scanner.next();
            Printer.print("Conferma password: ");
            String confermaPassword = scanner.next();
            if (!password.equals(confermaPassword)) {
                Printer.errorPrint("Le password non corrispondono");
                return;
            }

            // Chiedo ruolo
            UserRegistrationController userRegistrationController = new UserRegistrationController();
            Printer.print("Ruolo[psicolgo/paziente]: ");
            String role = scanner.next();
            // Gestione del ruolo
            switch (role) {
                case "psicologo" -> {
                    PsychologistBean psychologistBean = new PsychologistBean(
                            new CredentialsBean(email, password, Role.PSYCHOLOGIST),
                            nome, surname, city, null, false, false
                    );
                    userRegistrationController.registerPsychologist(psychologistBean);
                    Printer.printlnGreen("Registrazione effettuata con successo come psicologo.");
                    goNext(contextSM,new HomePsychologistCLI(psychologistBean));
                }
                case "paziente" -> {
                    PatientBean patientBean = new PatientBean(
                            new CredentialsBean(email, password, Role.PATIENT),
                            nome, surname, city, null, false, false
                    );
                    userRegistrationController.registerPatient(patientBean);
                    Printer.printlnGreen("Registrazione effettuata con successo come paziente.");
                    goNext(contextSM,new HomePatientCLI(patientBean));
                }
                default -> Printer.errorPrint("Ruolo non valido. Inserire 'psicologo' o 'paziente'.");
            }
        } catch (MailAlreadyExistsException e) {
            Printer.errorPrint("Errore: " + e.getMessage());
        } catch (Exception e) {
            Printer.errorPrint("Errore durante la registrazione. Riprova più tardi.");
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





