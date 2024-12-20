package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.RequestApplicationController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.RequestBean;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.BeanAndModelMapperFactory;
import com.theradiary.ispwtheradiary.engineering.patterns.observer.RequestManagerConcreteSubject;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;
import com.theradiary.ispwtheradiary.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestCLI extends AbstractState {
    protected RequestApplicationController requestApplicationController = new RequestApplicationController();
    private final BeanAndModelMapperFactory beanAndModelMapperFactory;
    private final RequestManagerConcreteSubject requestManagerConcreteSubject = RequestManagerConcreteSubject.getInstance();
    private final List<RequestBean> requestBeans = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    protected PsychologistBean user;

    public RequestCLI(PsychologistBean user) {
        this.user = user;
        this.beanAndModelMapperFactory = BeanAndModelMapperFactory.getInstance();
    }

    /*Azione per accettare o rifiutare richiesta*/
    @Override
    public void action(StateMachineImpl context) {
        int scelta;
        boolean running = true;
        while (running) {
            try {
                scelta = Integer.parseInt(scanner.nextLine().trim());
                switch (scelta) {
                    case (1):
                        displayRequests();
                        break;
                    case (2):
                        handleRequest(true);
                        break;
                    case (3):
                        handleRequest(false);
                        break;
                    case (4):
                        goBack(context);
                        running = false;
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida");
                        running = false;
                        break;
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint("Inserisci un numero");
            } catch (Exception e) {
                Printer.errorPrint("Errore durante l'elaborazione");
            }

        }
    }

    /*Carica le richieste*/
    private void loadRequests() {
        List<Request> requests = requestManagerConcreteSubject.getRequests();
        if (requests == null || requests.isEmpty()) {
            Printer.println("Non ci sono richieste");
            return;
        }
        requestBeans.clear();
        for (Request request : requests) {
            requestBeans.add(beanAndModelMapperFactory.fromBeanToModel(request, Request.class));
        }

    }

    /*Visualizza le richieste*/
    private void displayRequests() {
        if (requestBeans.isEmpty()) {
            Printer.println("Non ci sono richieste");
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

    /*Accetta o rifiuta la richiesta*/
    private void handleRequest(boolean accept) {
        if (requestBeans.isEmpty()) {
            Printer.println("Non ci sono richieste");
            return;
        }
        displayRequests();
        boolean validInput = false;
        while (!validInput) {
            try {
                Printer.println("Inserisci il numero della richiesta che vuoi gestire");
                String input = scanner.nextLine().trim();
                int requestIndex = Integer.parseInt(input.trim()) - 1;
                if (requestIndex < 0 || requestIndex >= requestBeans.size()) {
                    Printer.errorPrint("Numero non valido riprova");
                } else {
                    validInput = true;
                    //Elabora la richiesta
                    RequestBean requestBean = requestBeans.get(requestIndex);
                    requestApplicationController.deleteRequest(requestBean);
                    if (accept) {
                        PatientBean patientBean = requestBean.getPatientBean();
                        patientBean.setPsychologistBean(requestBean.getPsychologistBean());
                        requestApplicationController.addPsychologistToPatient(patientBean);
                        user.getPatientsBean().add(patientBean);
                        Printer.println("Richiesta accettata per:" + patientBean.getFullName());
                    } else {
                        Printer.println("Richiesta rifiutata per:" + requestBean.getPatientBean().getFullName());
                    }
                    requestBeans.remove(requestIndex);
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint("Inserisci un numero");
            }
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
}



