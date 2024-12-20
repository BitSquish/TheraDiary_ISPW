package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.RequestApplicationController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.Observer;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;
import com.theradiary.ispwtheradiary.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestCLI extends AbstractState implements Observer {

    /****************************PATTERN OBSERVER*********************************/
    protected final RequestApplicationController requestApplicationController = new RequestApplicationController();
    private final RequestManagerConcreteSubject requestManagerConcreteSubject;
    private final List<RequestBean> requestBeans = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    protected PsychologistBean user;

    // Construttore
    public RequestCLI(PsychologistBean user) {
        this.requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
        this.user = user;
        initializeObserver();
    }

    // Initialize Observer  RequestManagerConcreteSubject
    public void initializeObserver() {
        requestManagerConcreteSubject.addObserver(this);
    }

    @Override
    public void action(StateMachineImpl context) {
        boolean running = true;

        while (running) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> displayRequests();
                    case 2 -> handleRequest(true);
                    case 3 -> handleRequest(false);
                    case 0 -> {
                        goNext(context, new HomePsychologistCLI(user));
                        running = false;
                    }
                    default -> Printer.errorPrint("Scelta non valida. Inserisci un numero tra 1 e 4.");
                }

            } catch (NumberFormatException e) {
                Printer.errorPrint("Input non valido. Inserisci un numero.");
            } catch (Exception e) {
                Printer.errorPrint("Errore durante l'elaborazione: " + e.getMessage());
            }
        }
    }

    // Fa vedere tutte le richieste
    private void displayRequests() {
        if (requestBeans.isEmpty()) {
            Printer.println("Non ci sono richieste al momento.");
        } else {
            Printer.println("\n----- Lista delle Richieste -----\n");
            int index = 1;
            for (RequestBean request : requestBeans) {
                Printer.println(index + ". Richiesta da: " + request.getPatientBean().getFullName() + " | "
                        + request.getPatientBean().getCity() + " | " + request.getDate() + " | "
                        + request.getPatientBean().getModality());
                index++;
            }
        }
    }

    // Gestione delle richieste accetta o rifiuta
    private void handleRequest(boolean accept) {
        displayRequests();

        if (requestBeans.isEmpty()) {
            return;
        }

        Printer.println("Inserisci il numero della richiesta che vuoi gestire:");
        try {
            int selectedIndex = Integer.parseInt(scanner.nextLine().trim());

            if (selectedIndex > 0 && selectedIndex <= requestBeans.size()) {
                RequestBean requestBean = requestBeans.get(selectedIndex - 1);
                requestApplicationController.deleteRequest(requestBean);

                if (accept) {
                    PatientBean patientBean = requestBean.getPatientBean();
                    patientBean.setPsychologistBean(requestBean.getPsychologistBean());
                    requestApplicationController.addPsychologistToPatient(patientBean);
                    user.getPatientsBean().add(patientBean);
                }

                requestBeans.remove(requestBean);
                Printer.printlnGreen("Richiesta gestita con successo.");
            } else {
                Printer.errorPrint("Scelta non valida. Inserisci un numero tra 1 e " + requestBeans.size() + ".");
            }
        } catch (NumberFormatException e) {
            Printer.errorPrint("Input non valido. Inserisci un numero.");
        }
    }

    // Update lista richiesta
    @Override
    public void update() {
        List<Request> requests = requestManagerConcreteSubject.getRequests();

        requestBeans.clear();
        if (requests != null) {
            for (Request request : requests) {
                requestBeans.add(request.toBean());
            }
        }
    }



    /****************************PATTERN STATE*********************************/
    @Override
    public void showMenu() {
        Printer.println("\n1. Visualizza le richieste");
        Printer.println("2. Accetta richiesta");
        Printer.println("3. Rifiuta richiesta");
        Printer.println("0. Indietro");
        Printer.print("Opzione scelta: ");
    }


    @Override
    public void stampa() {
        Printer.println("\n------------------- Richieste -------------------");
        Printer.println("Ciao " + user.getFullName() + ", scegli cosa vuoi fare:");
    }

    @Override
    public void enter(StateMachineImpl context) {
        update(); // Load requests when entering the state
        stampa();
        showMenu();
    }
}


