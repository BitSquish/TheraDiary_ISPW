package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentPatientCLI extends AbstractState {
    private final PsychologistBean psychologistBean;
    private final PatientBean user;
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentController appointmentController = new AppointmentController();
    public AppointmentPatientCLI(PatientBean user){
        this.psychologistBean = user.getPsychologistBean();
        this.user=user;
    }
    Scanner scanner = new Scanner(System.in);
    @Override
    public void action(StateMachineImpl context) {
        boolean exit = false;
        while (!exit) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        viewAppointments();
                        break;
                    case 2:
                        bookAppointment();
                        break;
                    case 3:
                        exit = true;
                        goNext(context, new HomePatientCLI(user));
                        break;
                    default:
                        Printer.errorPrint("Input non valido");
                        break;
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint("Errore scelta non valida");

            }
        }


    }
    private void viewAppointments(){
        if(psychologistBean==null){
            Printer.errorPrint("Non hai uno psicologo associato. Cerca uno psicologo per poter prenotare un appuntamento");
            goNext(new StateMachineImpl(),new SearchCLI(user));
        }
        appointmentController.loadAllAppointments(allAppointments,psychologistBean);
        if(allAppointments.isEmpty()){
            Printer.errorPrint("Non hai appuntamenti prenotati");
        }
        Printer.printlnBlue("Appuntamenti disponibili:");
        for(AppointmentBean appointment:allAppointments){
            Printer.println("-"+appointment);
        }
    }
    private void bookAppointment(){
        if(psychologistBean==null){
            Printer.errorPrint("Non hai uno psicologo associato. Cerca uno psicologo per poter prenotare un appuntamento");
            goNext(new StateMachineImpl(),new SearchCLI(user));
        }
        appointmentController.loadAvailableAppointments(allAppointments,user);
        if(allAppointments.isEmpty()){
            Printer.errorPrint("Non ci sono appuntamenti disponibili da prenotare");
            return;
        }
        Printer.printlnBlue("Giorni disponibili:");
        List<String> days=allAppointments.stream().map(app-> DayOfTheWeek.translateDay(app.getDay().getId())).distinct().toList();
        for(int i=0;i<days.size();i++){
            Printer.println((i+1)+". "+days.get(i));
        }
        Printer.print("Seleziona il giorno (numero):");
        int dayIndex=Integer.parseInt(scanner.nextLine())-1;
        if (dayIndex<0 || dayIndex>=days.size()){
            Printer.errorPrint("Scelta non valida");
        }
        String selectedDay=days.get(dayIndex);
        DayOfTheWeek day=DayOfTheWeek.valueOf(selectedDay);

        Printer.printlnBlue("Fasce orarie disponibili per"+ selectedDay+":");
        List<String> timeSlots=allAppointments.stream().filter(app->DayOfTheWeek.translateDay(app.getDay().getId()).equals(selectedDay)).map(app-> TimeSlot.translateTimeSlot(app.getTimeSlot().getId())).distinct().toList();
        for(int i=0;i<timeSlots.size();i++){
            Printer.println((i+1)+". "+timeSlots.get(i));
        }
        Printer.print("Seleziona la fascia oraria (numero):");
        int timeSlotIndex=Integer.parseInt(scanner.nextLine())-1;
        if (timeSlotIndex<0 || timeSlotIndex>=timeSlots.size()){
            Printer.errorPrint("Scelta non valida");
        }
        String selectedTimeSlot=timeSlots.get(timeSlotIndex);
        TimeSlot timeSlot=TimeSlot.fromStringToTimeSlot(selectedTimeSlot);
        if(!appointmentController.hasAlreadySentARequest(user,day,timeSlot,allAppointments)){
            AppointmentBean appointmentBean=new AppointmentBean(psychologistBean,day,timeSlot,user.getCredentialsBean().getMail());
            appointmentController.askForAnAppointment(appointmentBean);
            Printer.printGreen("Richiesta inviata con successo");
        }else{
            Printer.errorPrint("Hai già fatto richiesta per questa fascia oraria. Attendi che il tuo psicologo confermi o rifiuti l'appuntamento.");
        }
    }
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza appuntamenti");
        Printer.println("2.Prenota appuntamento");
        Printer.println("3.Indietro");
        Printer.println("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------I tuoi appuntamenti-------------------");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }




}


