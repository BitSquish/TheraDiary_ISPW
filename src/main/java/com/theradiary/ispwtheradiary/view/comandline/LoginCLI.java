package com.theradiary.ispwtheradiary.view.comandline;


import com.theradiary.ispwtheradiary.controller.LoginController;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.CredentialsBean;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;

import java.util.Scanner;

public class LoginCLI extends AbstractState {
    LoggedUserBean user;
    private final Scanner scanner = new Scanner(System.in);
    private final LoginController login = new LoginController();

    @Override
    public void action(StateMachineImpl context) {
        //metodo effettivo per il login
        Printer.println("---Email: ");
        String email = scanner.next();
        Printer.println("---Password: ");
        String password = scanner.next();
        //controllo se l'utente è registrato
        //se si, setto l'utente loggato
        //altrimenti stampo errore

        try {
            //creazione delle credenziali
            CredentialsBean credentials = new CredentialsBean(email, password, null);
            //login e verifica credenziali
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
        } catch (WrongEmailOrPasswordException | NoResultException e) {
            Printer.errorPrint("Email o password errati");
            action(context);
        }
    }
    private PsychologistBean setupPsychologist(LoginController login, CredentialsBean credentials) throws NoResultException {
        PsychologistBean psychologistBean = new PsychologistBean(credentials);
        login.retrievePsychologist(psychologistBean);
        return psychologistBean;
    }
    private PatientBean setupPatient(LoginController login, CredentialsBean credentials) throws NoResultException {
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
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------- LOGIN --------------");
    }
}

