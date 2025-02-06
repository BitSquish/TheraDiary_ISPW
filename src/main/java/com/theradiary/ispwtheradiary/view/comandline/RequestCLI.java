package com.theradiary.ispwtheradiary.view.comandline;

import com.theradiary.ispwtheradiary.controller.RequestApplicationController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.beans.RequestBean;
import com.theradiary.ispwtheradiary.patterns.observer.Observer;
import com.theradiary.ispwtheradiary.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;
import com.theradiary.ispwtheradiary.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestCLI extends AbstractState implements Observer {
    /*****************************Pattern observer**********************************/
    protected RequestApplicationController requestApplicationController = new RequestApplicationController();

    private final RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
    private final List<RequestBean> requestBeans = new ArrayList<>();

    public RequestCLI(PsychologistBean user) {
        this.user = user;
        this.requestManagerConcreteSubject.addObserver(this);
    }

    /*****************************************************************************/
    private final Scanner scanner = new Scanner(System.in);
    protected PsychologistBean user;
    private static final String NON_SONO_PRESENTI_RICHIESTE = "Non sono presenti richieste";

    /*Azione per accettare o rifiutare richiesta*/
    @Override
    public void action(StateMachineImpl context) {
        boolean running = true;
        while (running) {
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> displayRequests();
                    case 2 -> handleRequest(true); // accetta richiesta
                    case 3 -> handleRequest(false); // rifiuta richiesta
                    case 4 -> {
                        goBack(context);
                        running = false;
                    }
                    default -> {
                        Printer.errorPrint(SCELTA_NON_VALIDA);
                        running = false;
                    }
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint(SCELTA_NON_VALIDA);
            } catch (Exception e) {
                Printer.errorPrint("Errore durante l'elaborazione");
            }

        }
    }

    /*Carica le richieste*/
    private void loadRequests() {
        List<Request> requests = requestManagerConcreteSubject.getRequests();
        if (requests == null || requests.isEmpty()) {
            Printer.println(NON_SONO_PRESENTI_RICHIESTE);
            return;
        }
        requestBeans.clear();
        for (Request request : requests) {
            requestBeans.add(requestApplicationController.createRequestBean(request));
        }

    }

    /*Visualizza le richieste*/
    private void displayRequests() {
        if (requestBeans.isEmpty()) {
            Printer.println(NON_SONO_PRESENTI_RICHIESTE);
        }else {
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

    /*Accetta o rifiuta la richiesta*/
    private void handleRequest(boolean accept) {
        if(requestBeans.isEmpty()) {
            Printer.errorPrint(NON_SONO_PRESENTI_RICHIESTE);
            return;
        }
        displayRequests();
        int requestIndex=getRequestIndex();
        if(requestIndex>=0 && requestIndex<requestBeans.size()) {
            RequestBean requestBean = requestBeans.get(requestIndex);
            processRequest(requestBean, accept);
            requestBeans.remove(requestIndex);
            loadRequests();
        }
    }
    private int getRequestIndex() {
        int requestIndex = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                Printer.print("Inserisci il numero della richiesta da gestire: ");
                requestIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if(requestIndex<0 || requestIndex>=requestBeans.size()) {
                    Printer.errorPrint(SCELTA_NON_VALIDA);
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint(SCELTA_NON_VALIDA);
            }
        }
        return requestIndex;
    }
    private void processRequest(RequestBean requestBean, boolean accept) {
        requestApplicationController.deleteRequest(requestBean);
        if (accept) {
            PatientBean patientBean = requestBean.getPatientBean();
            patientBean.setPsychologistBean(requestBean.getPsychologistBean());
            requestApplicationController.addPsychologistToPatient(patientBean);
            user.getPatientsBean().add(patientBean);
            Printer.println("Richiesta accettata per:"+patientBean.getFullName());
        } else {
            Printer.println("Richiesta rifiutata per:"+requestBean.getPatientBean().getFullName());
        }
    }

    /*-----------------Pattern state---------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza le richieste");
        Printer.println("2.Accetta richiesta");
        Printer.println("3.Rifiuta richiesta");
        Printer.println("4.Indietro");
        Printer.print("Opzione scelta:");
    }

    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Richieste-------------------");
        Printer.println("Ciao" + " " + this.user.getFullName() + ",scegli cosa vuoi fare:");
    }

    @Override
    public void enter(StateMachineImpl context) {
        loadRequests();
        stampa();
        showMenu();
    }
    /*Pattern observer*/
    @Override
    public void update() {
        loadRequests();
        if(requestBeans.isEmpty()) {
            Printer.println(NON_SONO_PRESENTI_RICHIESTE);
        } else {
            Printer.println("Aggiornamento richieste");
        }
    }
}



