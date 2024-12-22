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
        loadAllAppointments();
    }

    @Override
    public void action(StateMachineImpl context) {
        boolean exit = false;
        while (!exit) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> showDayAppointments();
                    case 2 -> saveAppointment();
                    case 3 -> {
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
    private void showDayAppointments(){
        Printer.println("Seleziona il giorno della settimana");
        for(DayOfTheWeek day : DayOfTheWeek.values()){
            Printer.println(day.ordinal() + 1 + ". " + day);
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
        } catch (NumberFormatException e) {
            Printer.errorPrint("Errore scelta non valida");
        }catch (Exception e){
            Printer.errorPrint("Errore nella visualizzazione degli appuntamenti");
        }
    }
    private void saveAppointment(){
        List<AppointmentBean> appointmentToAdd=new ArrayList<>();
        for(DayOfTheWeek day: DayOfTheWeek.values()){
            Printer.println("Inserisci gli appuntamenti per il giorno"+day+":");
            for(TimeSlot timeSlot:TimeSlot.values()){
                Printer.print("Vuoi inserire un appuntamento per la fascia oraria"+timeSlot+"? (in presenza: 'p', online: 'o', entrambi: 'b', nessuno: 'n')");
                String input= scanner.nextLine().toLowerCase();
                if(input.equals("p")||input.equals("b")||input.equals("o")){
                    boolean inPerson=input.equals("p")||input.equals("b");
                    boolean online=input.equals("o")||input.equals("b");
                    AppointmentBean appointmentBean=createAppointmentBean(day,timeSlot,inPerson,online);
                    setPatient(appointmentBean);
                    appointmentToAdd.add(appointmentBean);
                }
            }

        }
        if(!appointmentToAdd.isEmpty()){
            appointmentPs.saveAppointments(user,appointmentToAdd);
            allAppointments.clear();
            allAppointments.addAll(appointmentToAdd);
            updateVisitModality(appointmentToAdd);
            Printer.println("Appuntamenti salvati con successo");
        }else{
            Printer.errorPrint("Nessun appuntamento da salvare");
        }
    }
    private AppointmentBean createAppointmentBean(DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online){
        return new AppointmentBean(user,day,timeSlot,inPerson,online);
    }
    private void setPatient(AppointmentBean appointmentBean){
        for(AppointmentBean app:allAppointments){
            if(app.getDay().equals(appointmentBean.getDay())&&app.getTimeSlot().equals(appointmentBean.getTimeSlot())){
                appointmentBean.setPatientBean(app.getPatientBean());
                break;
            }
        }
    }
    private void loadAllAppointments(){
        appointmentPs.loadAllAppointments(allAppointments,user);
        Printer.println("Appuntamenti caricati con successo");
    }
    private void updateVisitModality(List<AppointmentBean> appointmentsToAdd){
       boolean hasChanged=appointmentPs.changeModality(appointmentsToAdd);
         if(hasChanged){
              user.setInPerson(true);
              user.setOnline(true);
              Printer.println("Modalità cambiata con successo");
         }
    }
    private void printAppointments(String modality, List<TimeSlot> timeSlots){
        Printer.println("----"+modality+"---");
        if (timeSlots.isEmpty()) {
            Printer.println("Nessun appuntamento disponibile.");
        } else {
            for (TimeSlot timeSlot : timeSlots) {
                Printer.println("| " + timeSlot + " |");
            }
        }
    }
    /**********************************Pattern State*********************************************/
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza appuntamenti per un giorno specifico ");
        Printer.println("2.Salva appuntamento");
        Printer.println("3.Esci");
        Printer.print("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------I tuoi appuntamenti-------------------");
    }
}


