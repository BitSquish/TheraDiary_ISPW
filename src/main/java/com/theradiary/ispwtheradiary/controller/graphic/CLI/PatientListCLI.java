package com.theradiary.ispwtheradiary.controller.graphic.CLI;

import com.theradiary.ispwtheradiary.controller.application.Account;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class PatientListCLI extends AbstractState {
    protected PsychologistBean psychologistBean;
    public PatientListCLI(PsychologistBean psychologistBean) {
        this.psychologistBean = psychologistBean;
    }
    Scanner scanner = new Scanner(System.in);
    @Override
    public void action(StateMachineImpl context) {
        try {
            Account account = new Account();
            List<PatientBean> patients = account.retrievePatientList(psychologistBean);
            if (patients.isEmpty()) {
                Printer.println("Non hai pazienti");
                goBack(context);
            } else {
                Printer.printlnBlue("-------------------Lista pazienti-------------------");
                for (int i = 0; i < patients.size(); i++) {
                    Printer.println((i + 1) + ". " + patients.get(i).getFullName() + " " + patients.get(i).getCity());
                }
                Printer.println("0. Indietro");
                Printer.print("Seleziona un paziente e visualizza i suoi task:");
                int selectedIndex = scanner.nextInt();
                if (selectedIndex > 0 && selectedIndex <= patients.size()) {
                    PatientBean selectedPatient = patients.get(selectedIndex - 1);
                    goNext(context, new TaskPsychologistCLI(selectedPatient));
                } else if (selectedIndex == 0) {
                    goBack(context);
                } else {
                    Printer.errorPrint("Scelta non valida, inserisci un numero tra 1 e" + patients.size());
                }
            }
        }catch (Exception e){
            Printer.errorPrint("Errore durante il recupero della lista pazienti");
            goBack(context);
        }

    }

}
