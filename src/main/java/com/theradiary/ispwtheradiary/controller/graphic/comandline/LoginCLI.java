package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.Scanner;

public class LoginCLI extends AbstractState {
    LoggedUserBean user;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineImpl context) {
        //metodo effettivo per il login
        Printer.println("   Email: ");
        String email = scanner.next();
        Printer.println("   Password: ");
        String password = scanner.next();
        //controllo se l'utente è registrato
        //se si, setto l'utente loggato
        //altrimenti stampo errore

        try {
            //creazione delle credenziali
            CredentialsBean credentials = new CredentialsBean(email, password, null);
            //login e verifica credenziali
            LoginController login = new LoginController();
            login.log(credentials);
            //determino il ruolo e prepara lo stato successivo
            AbstractState homeCLI;
            if(credentials.getRole().equals(Role.PSYCHOLOGIST)){
                user = setupPsychologist(login, credentials);
                homeCLI = new HomePsychologistCLI(user);
            } else {
                user = setupPatient(login, credentials);
                homeCLI = new HomePatientCLI(user);
            }
            goNext(context, homeCLI);
        } catch (WrongEmailOrPasswordException e) {
            System.out.println("Email o password errati");
            action(context);
        }
    }
    private PsychologistBean setupPsychologist(LoginController login, CredentialsBean credentials) {
        PsychologistBean psychologistBean = new PsychologistBean(credentials);
        login.retrievePsychologist(psychologistBean);
        return psychologistBean;
    }
    private PatientBean setupPatient(LoginController login, CredentialsBean credentials) {
        PatientBean patientBean = new PatientBean(credentials);
        login.retrievePatient(patientBean);
        return patientBean;
    }

    /*Pattern state*/
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
    }

    @Override
    public void exit(StateMachineImpl context) {
    }

    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------- LOGIN --------------");
    }
}

