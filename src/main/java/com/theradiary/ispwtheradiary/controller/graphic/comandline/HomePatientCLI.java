package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.InitialState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.Scanner;

public class HomePatientCLI extends AbstractState {
    protected LoggedUserBean user;
    public HomePatientCLI(LoggedUserBean user) {
        this.user = user;
    }
    private final Scanner scanner = new Scanner(System.in);
    /* Opzione menu dove voglio andare */
    @Override
    public void action(StateMachineImpl context) {
        int scelta;
        while ((scelta = scanner.nextInt()) != 0) {
            try {
                switch (scelta) {
                    case (1):
                        goNext(context, new TaskPatientCLI((PatientBean) user));
                        break;
                    case (2):
                        goNext(context, new AppointmentPatientCLI());
                        break;
                    case (3):
                        goNext(context, new SearchCLI((PatientBean) user));
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida");
                        break;
                }
            } catch (Exception e) {
                Printer.errorPrint("Errore nella scelta");
                scanner.nextLine();
            }

        }
        goNext(context, new InitialState());
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza le task");
        Printer.println("2.Visualizza appuntamenti");
        Printer.println("3.Cerca uno psicologo");
        Printer.println("0.Logout");
        Printer.print("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella home di"+ " " + user.getFullName()+"-------------------");
        Printer.println("Ciao" + " "+ this.user.getFullName() + ",scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }
}