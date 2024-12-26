package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.AppointmentController;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentPsychologistCLI extends AbstractState {
    protected PsychologistBean user;
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentController appointmentPs = new AppointmentController();
    private final Scanner scanner = new Scanner(System.in);
    public AppointmentPsychologistCLI(PsychologistBean user) {
        this.user = user;
    }



    @Override
    public void action(StateMachineImpl context) {
        boolean exit = false;
        while (!exit) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> modifyAppointments();
                    case 2 ->goToSummary();
                    case 0 -> {
                        exit = true;
                        goNext(context, new HomePsychologistCLI(user));
                    }
                    default -> Printer.errorPrint("Input non valido");
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint("Errore scelta non valida");

            }
        }
    }
    private void modifyAppointments(){
        getAllAppointments();
        Printer.println("Seleziona il giorno della settimana");
        for(int i=0;i<DayOfTheWeek.values().length;i++){
            Printer.println((i+1)+". "+DayOfTheWeek.translateDay(i));
        }
        try {
            int dayChoice = Integer.parseInt(scanner.nextLine()) - 1;
            if (dayChoice < 0 || dayChoice >= DayOfTheWeek.values().length) {
                Printer.errorPrint("Scelta non valida");
                return;
            }
            DayOfTheWeek selectedDay = DayOfTheWeek.values()[dayChoice];
            List<TimeSlot> inPersonTimeSlots = new ArrayList<>();
            List<TimeSlot> onlineTimeSlots = new ArrayList<>();
            appointmentPs.getDayOfTheWeekAppointments(allAppointments, selectedDay, inPersonTimeSlots, onlineTimeSlots);
            Printer.println("Appuntamenti per" + selectedDay + ":");
            printAppointments("In presenza", inPersonTimeSlots);
            printAppointments("Online", onlineTimeSlots);
            Printer.print("Modifica le fasce orarie. Inserisci 1 per in presenza, 2 per online 3 per entrambi, 0 per nessuno.");
            List<AppointmentBean> newAppointments = new ArrayList<>();
            for(int i=0;i<TimeSlot.values().length;i++){
               TimeSlot slot=TimeSlot.values()[i];
                Printer.print(slot+":" );
                int modality = Integer.parseInt(scanner.nextLine());
                if(modality>0){
                    boolean inPerson = modality == 1 || modality == 3;
                    boolean online = modality == 2 || modality == 3;
                    AppointmentBean appointmentBean = getAppointmentBean(selectedDay, slot, inPerson, online);
                    setPatientAndAvailability(appointmentBean);
                    newAppointments.add(appointmentBean);
                }

            }
            allAppointments.removeIf(appointment-> appointment.getDay().equals(selectedDay));
            allAppointments.addAll(newAppointments);
            appointmentPs.saveAppointments(user,allAppointments);
            Printer.println("Appuntamenti modificati e salvati  con successo");
        } catch (NumberFormatException e) {
            Printer.errorPrint("Errore scelta non valida");
        }
    }
    private void goToSummary(){
        getAllAppointments();
        Printer.println("-------------Riepilogo Appuntamenti-------------");
        for (AppointmentBean appointment : allAppointments) {
            if(!appointment.isAvailable()){
                Printer.println(appointment.toString());
            }
        }
    }

    private AppointmentBean getAppointmentBean(DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online){
        return new AppointmentBean(user,day,timeSlot,inPerson,online);
    }
    private void setPatientAndAvailability(AppointmentBean appointmentBean){
        for(AppointmentBean app:allAppointments){
            if(app.getDay().equals(appointmentBean.getDay())&&app.getTimeSlot().equals(appointmentBean.getTimeSlot()) && app.getPatientBean()!=null){
                appointmentBean.setPatientBean(app.getPatientBean());
                appointmentBean.setAvailable(app.isAvailable());
                appointmentBean.setInPerson(app.isInPerson());
                appointmentBean.setOnline(app.isOnline());
                break;
            }
        }
    }
    private void printAppointments(String modality, List<TimeSlot> timeSlots){
        Printer.println("----"+modality+"---");
        if (timeSlots.isEmpty()) {
            Printer.println("Nessun appuntamento disponibile.");
        } else {
            timeSlots.forEach(timeSlot->Printer.println("|"+timeSlot+"|"));
        }
    }
    private void getAllAppointments(){
        appointmentPs.loadAllAppointments(allAppointments, user);
    }

    /**********************************Pattern State*********************************************/
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();

    }
    @Override
    public void showMenu() {
        Printer.println("1.Modifica Appuntamenti ");
        Printer.println("2.Riepilogo appuntamenti");
        Printer.println("0.Esci");
        Printer.print("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------I tuoi appuntamenti-------------------");
    }


}


