package com.theradiary.ispwtheradiary.view.comandline;


import com.theradiary.ispwtheradiary.controller.AppointmentPsController;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.theradiary.ispwtheradiary.engineering.enums.TimeSlot.translateTimeSlot;

public class AppointmentPsychologistCLI extends AbstractState {
    protected PsychologistBean user;
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentPsController appointmentPs = new AppointmentPsController();
    private final Scanner scanner = new Scanner(System.in);
    public AppointmentPsychologistCLI(PsychologistBean user) {
        this.user = user;
        loadAppointments();
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
                    default -> Printer.errorPrint(SCELTA_NON_VALIDA);
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint(SCELTA_NON_VALIDA);

            }
        }
    }
    private int promptUserInput(){
        Printer.print("Opzione scelta:");
        return Integer.parseInt(scanner.nextLine());
    }
    private void modifyAppointments(){
        Printer.println("Seleziona il giorno della settimana");
        DayOfTheWeek selectedDay=selectedDayOfTheWeek();
        if(selectedDay==null) return;
        List<TimeSlot> inPersonSlots= new ArrayList<>();
        List<TimeSlot> onlineSlots= new ArrayList<>();
        appointmentPs.getDayOfTheWeekAppointments(allAppointments,selectedDay,inPersonSlots,onlineSlots);
        displayAppointments(selectedDay,inPersonSlots,onlineSlots);
        updateAppointment(selectedDay);
        saveAppointments(selectedDay);
    }
    private DayOfTheWeek selectedDayOfTheWeek(){
        for (DayOfTheWeek day : DayOfTheWeek.values()) {
            Printer.println(day.getId() + ". " + DayOfTheWeek.translateDay(day.getId()));
        }
        try {
            int choice = promptUserInput();
            return Arrays.stream(DayOfTheWeek.values())// converto l'array in uno stream su cui poi lavorare
                    .filter(day -> day.getId() == choice)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(SCELTA_NON_VALIDA));
        } catch (IllegalArgumentException e) {
            Printer.errorPrint(e.getMessage());
            return null;
        }
    }
    private void displayAppointments(DayOfTheWeek selectedDay, List<TimeSlot> inPersonSlots, List<TimeSlot> onlineSlots) {
        Printer.println("Appuntamenti per " + DayOfTheWeek.translateDay(selectedDay.getId()) + ":");
        printAppointments("In presenza", inPersonSlots);
        printAppointments("Online", onlineSlots);
    }
    private void updateAppointment(DayOfTheWeek selectedDay) {
        List<AppointmentBean> newAppointments = new ArrayList<>();
        Printer.println("Modifica le fasce orarie (1 per in presenza, 2 per online, 3 per entrambi, 0 per nessuno):");

        for (TimeSlot slot : TimeSlot.values()) {
            Printer.print(translateTimeSlot(slot.getId()) + ": ");
            int modality = Integer.parseInt(scanner.nextLine());
            if (modality > 0) {
                newAppointments.add(createAppointment(selectedDay, slot, modality));
            }
        }

        allAppointments.removeIf(app -> app.getDay().equals(selectedDay));
        allAppointments.addAll(newAppointments);
    }
    private AppointmentBean createAppointment(DayOfTheWeek day, TimeSlot slot, int modality) {
        boolean inPerson = modality == 1 || modality == 3;
        boolean online = modality == 2 || modality == 3;
        AppointmentBean appointment = new AppointmentBean(user, day, slot, inPerson, online);
        copyExistingDetails(appointment);
        return appointment;
    }
    private void copyExistingDetails(AppointmentBean appointment) {
        allAppointments.stream()
                .filter(app -> app.getDay().equals(appointment.getDay()) &&
                        app.getTimeSlot().equals(appointment.getTimeSlot()) &&
                        app.getPatientBean() != null)
                .findFirst()
                .ifPresent(existing -> {
                    appointment.setPatientBean(existing.getPatientBean());
                    appointment.setAvailable(existing.isAvailable());
                    appointment.setInPerson(existing.isInPerson());
                    appointment.setOnline(existing.isOnline());
                });
    }
    private void saveAppointments(DayOfTheWeek selectedDay) {
        appointmentPs.saveAppointments(user, allAppointments);
        Printer.println("Appuntamenti per " + DayOfTheWeek.translateDay(selectedDay.getId()) + " salvati con successo.");
    }
    private void goToSummary() {
        Printer.println("-------------Riepilogo Appuntamenti-------------");
        allAppointments.stream()
                .filter(appointment -> !appointment.isAvailable())
                .forEach(appointment -> Printer.println(appointment.toString()));
    }
    private void printAppointments(String modality, List<TimeSlot> timeSlots) {
        Printer.println("---- " + modality + " ----");
        if (timeSlots.isEmpty()) {
            Printer.println("Nessun appuntamento disponibile.");
        } else {
            timeSlots.forEach(slot -> Printer.println("| " + translateTimeSlot(slot.getId()) + " |"));
        }
    }
    private void loadAppointments() {
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


