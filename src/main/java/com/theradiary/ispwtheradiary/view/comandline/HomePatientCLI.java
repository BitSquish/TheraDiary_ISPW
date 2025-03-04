package com.theradiary.ispwtheradiary.view.comandline;

import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.LoggedUserBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.InitialState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;

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
        boolean exit=false;
        while (!exit) {
            try {
                int choice= Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> goNext(context, new TaskAndToDoPtCLI((PatientBean) user));
                    case 2 -> goNext(context, new AppointmentPatientCLI((PatientBean) user));
                    case 3 -> goNext(context, new SearchCLI((PatientBean) user));
                    case 0 -> {
                        exit = true;
                        goNext(context, new InitialState());
                    }
                    default -> Printer.errorPrint(SCELTA_NON_VALIDA);
                }
            } catch (Exception e) {
                Printer.errorPrint(SCELTA_NON_VALIDA);
                scanner.nextLine();
            }

        }
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza le task");
        Printer.println("2.Appuntamenti");
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