package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.InitialState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class HomePsychologistCLI extends AbstractState {
    protected LoggedUserBean user;
    public HomePsychologistCLI(LoggedUserBean user) {this.user = user;}
    private final Scanner scanner = new Scanner(System.in);
    /* Opzione menu dove voglio andare */
    @Override
    public void action(StateMachineImpl context) {
        boolean exit=false;
        // Inizializza i dati richiesti per gli stati
        while (!exit) {
            try {
                int choice= Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> goNext(context, new PatientListCLI((PsychologistBean) user));
                    case 2 -> goNext(context, new AppointmentPsychologistCLI((PsychologistBean) user));
                    case 3 -> goNext(context, new MedicalOfficeCLI(user));
                    case 4 -> goNext(context, new RequestCLI((PsychologistBean) user));
                    case 0 -> {
                        exit = true;
                        goNext(context, new InitialState());
                    }
                    default -> Printer.errorPrint("Input non valido");
                }
            } catch (Exception e) {
                Printer.errorPrint("Errore scelta non valida");
                scanner.nextLine();
            }

        }
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza le task");
        Printer.println("2.Visualizza appuntamenti");
        Printer.println("3.Registra studio medico");
        Printer.println("4.Visualizza richieste");
        Printer.println("0.Logout");
        Printer.print("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella home dello psicologo-------------------");
        Printer.println("Ciao" + " " + this.user.getFullName() + ",scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }
}