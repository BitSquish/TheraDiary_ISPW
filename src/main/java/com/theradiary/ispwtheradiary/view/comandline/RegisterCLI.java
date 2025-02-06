
package com.theradiary.ispwtheradiary.view.comandline;

import com.theradiary.ispwtheradiary.controller.LoginController;
import com.theradiary.ispwtheradiary.controller.UserRegistrationController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;
import javafx.scene.control.Label;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RegisterCLI extends AbstractState {



    private final Scanner scanner = new Scanner(System.in);
    private final LoginController login = new LoginController();
    private final UserRegistrationController userRegistration = new UserRegistrationController();

    @Override
    public void action(StateMachineImpl contextSM) {
        //metodo effettivo per la registrazione
        try {
            //Chiedo informazioni
            String nome=prompt("Nome: ");
            String surname=prompt("Cognome: ");
            String city=prompt("Città: ");
            String email=prompt("Email: ");
            if (isValidMail(email, null)) {
                Printer.errorPrint("Email non valida");
                return;
            }
            String password=prompt("Password: ");
            boolean[] visitModes=getVisitModes();
            if( visitModes.length==0) {Printer.errorPrint("Errore durante la scelta delle modalità di visita");return;}//se la scelta non è valida
            String description=prompt("Descrizione: ");
            String role=prompt("Ruolo (psicologo/paziente): ").toLowerCase();
            //Creazione oggetti e registrazione utente
            switch (role){
                case "psicologo":
                    PsychologistBean psychologistBean = new PsychologistBean(new CredentialsBean(email,password,Role.PSYCHOLOGIST),nome,surname,city,description,visitModes[0],visitModes[1]);
                    userRegistration.registerPsychologist(psychologistBean);
                    Printer.println("Registrazione avvenuta con successo");
                    login.log(psychologistBean.getCredentialsBean());
                    goNext(contextSM,new HomePsychologistCLI(psychologistBean));
                    break;
                case "paziente":
                    PatientBean patientBean = new PatientBean(new CredentialsBean(email,password,Role.PATIENT),nome,surname,city,description,visitModes[0],visitModes[1]);
                    userRegistration.registerPatient(patientBean);
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
        Printer.print(message);
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
            return switch (choice) {
                case 1 -> new boolean[]{true, false};
                case 2 -> new boolean[]{false, true};
                case 3 -> new boolean[]{true, true};
                default -> {
                    Printer.errorPrint(SCELTA_NON_VALIDA);
                    yield new boolean[0];
                }
            };
        } catch (InputMismatchException e) {
            Printer.errorPrint("Input non valido. Inserisci un numero tra 1 e 3");
            return new boolean[0];
        }

    }
    public static boolean isValidMail(String mail, Label errorMessage) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!mail.matches(emailRegex)) {
            errorMessage.setText("Mail non valida");
            errorMessage.setVisible(true);
            return true;
        }
        return false;
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
