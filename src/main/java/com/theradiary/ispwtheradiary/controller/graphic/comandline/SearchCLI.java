package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.SearchController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchCLI extends AbstractState {
    protected PatientBean user;
    public SearchCLI(PatientBean user) {
        this.user = user;
    }
    Scanner scanner = new Scanner(System.in);
    /*Input parametri per la ricerca*/
    @Override
    public void action(StateMachineImpl context) {
        Printer.println("Nome:");
        String name = scanner.nextLine().trim();
        Printer.println("Cognome:");
        String surname = scanner.nextLine().trim();
        Printer.println("Città:");
        String city = scanner.nextLine().trim();
        Printer.println("Visita in presenza: (true/false)");
        String inPresenceString = scanner.nextLine().trim();
        boolean inPresence = !inPresenceString.isEmpty() && Boolean.parseBoolean(inPresenceString);
        Printer.println("Visita online:(true/false)");
        String onlineString = scanner.nextLine().trim();
        boolean online = !onlineString.isEmpty() && Boolean.parseBoolean(scanner.next());


        try {
            List<PsychologistBean> psychologists = new ArrayList<>();
            SearchController searchController = new SearchController();
            searchController.searchPsychologists(psychologists, name, surname, city, inPresence, online, false);
            if (psychologists.isEmpty()) {
                Printer.println("Nessun risultato trovato");
                goBack(context);
            } else {
                Printer.printlnBlue("-------------------Risultati ricerca-------------------");
                for (int i = 0; i < psychologists.size(); i++) {
                    Printer.println((i + 1) + ". " + psychologists.get(i).getFullName() + " " + psychologists.get(i).getCity() + " " + psychologists.get(i).getDescription());
                }
                Printer.println("0. Indietro");
                Printer.print("Seleziona uno psicologo e visualizza il suo profilo:");
                int selectedIndex = scanner.nextInt();
                if (selectedIndex > 0 && selectedIndex <= psychologists.size()) {
                    PsychologistBean selectedPsychologist = psychologists.get(selectedIndex - 1);
                    goNext(context, new PsychologistProfileCLI(user,selectedPsychologist));
                } else if (selectedIndex == 0) {
                    goBack(context);
                } else {
                    Printer.errorPrint("Scelta non valida, inserisci un numero tra 1 e" + psychologists.size());
                }

            }
        } catch (Exception e) {
            Printer.errorPrint("Errore nella ricerca");
            scanner.nextLine();
        }
    }
    /*-----------------Pattern state---------------*/
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Ricerca psicologo-------------------");
        Printer.println("Inserisci i dati per la ricerca (premi invio per saltare):");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
    }

}
