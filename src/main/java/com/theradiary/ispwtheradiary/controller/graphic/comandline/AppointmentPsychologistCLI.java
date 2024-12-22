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
    public AppointmentPsychologistCLI(PsychologistBean user) {
        this.user = user;
        getAllAppointments();
    }
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentController appointmentPs = new AppointmentController();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineImpl context) {
        boolean exit = false;
        while (!exit) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        showDayAppointments(scanner);
                        break;
                    case 2:
                        saveAppointment(scanner);
                        break;
                    case 3:
                        exit = true;
                        goNext(context, new HomePsychologistCLI(user));
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida");
                        break;
                }
            } catch (NumberFormatException e) {
                Printer.errorPrint("Errore scelta non valida");

            }
        }
    }
    private void showDayAppointments(Scanner scanner){
        Printer.println("Seleziona il giorno della settimana");
        for(DayOfTheWeek day : DayOfTheWeek.values()){
            Printer.println(day.ordinal() + 1 + ". " + day);
        }
        int dayChoice=Integer.parseInt(scanner.nextLine())-1;
        if(dayChoice<0 || dayChoice>=DayOfTheWeek.values().length){
            Printer.errorPrint("Scelta non valida");
        }
        DayOfTheWeek selectedDay=DayOfTheWeek.values()[dayChoice];
        List<TimeSlot> inPersonTimeSlots=new ArrayList<>();
        List<TimeSlot> onlineTimeSlots=new ArrayList<>();
        appointmentPs.getDayOfTheWeekAppointments(allAppointments,selectedDay,inPersonTimeSlots,onlineTimeSlots);

        Printer.println("Appuntamenti per"+selectedDay+":");
        Printer.println("----In presenza----");
        for(TimeSlot timeSlot:inPersonTimeSlots){
            Printer.println("|"+timeSlot+"|");
        }
        Printer.println("----Online----");
        for(TimeSlot timeSlot:onlineTimeSlots){
            Printer.println("|"+timeSlot+"|");
        }
    }
    private void saveAppointment(Scanner scanner){
        List<AppointmentBean> appointmentToAdd=new ArrayList<>();
        for(DayOfTheWeek day: DayOfTheWeek.values()){
            Printer.println("Inserisci gli appuntamenti per il giorno"+day+":");
            for(TimeSlot timeSlot:TimeSlot.values()){
                Printer.print("Vuoi inserire un appuntamento per la fascia oraria"+timeSlot+"? (in presenza: 'p', online: 'o', entrambi: 'b', nessuno: 'n')");
                String input= scanner.nextLine().toLowerCase();
                if(input.equals("p")||input.equals("b")||input.equals("o")){
                    boolean inPerson=input.equals("p")||input.equals("b");
                    boolean online=input.equals("o")||input.equals("b");
                    AppointmentBean appointmentBean=getAppointmentBean(day,timeSlot,inPerson,online);
                    setPatient(appointmentBean);
                    appointmentToAdd.add(appointmentBean);
                }
            }

        }
        if(!appointmentToAdd.isEmpty()){
            appointmentPs.saveAppointments(user,appointmentToAdd);
            allAppointments.clear();
            allAppointments.addAll(appointmentToAdd);
            changeModality(appointmentToAdd);
            Printer.println("Appuntamenti salvati con successo");
        }else{
            Printer.errorPrint("Nessun appuntamento da salvare");
        }
    }
    private AppointmentBean getAppointmentBean(DayOfTheWeek day, TimeSlot timeSlot, boolean inPerson, boolean online){
        return new AppointmentBean(user,day,timeSlot,inPerson,online);
    }
    private void setPatient(AppointmentBean appointmentBean){
        for(AppointmentBean app:allAppointments){
            if(app.getDay().equals(appointmentBean.getDay())&&app.getTimeSlot().equals(appointmentBean.getTimeSlot())){
                appointmentBean.setPatientBean(app.getPatientBean());
            }
        }
    }
    private void getAllAppointments(){
        appointmentPs.loadAllAppointments(allAppointments,user);
        Printer.println("Appuntamenti caricati con successo");
    }
    private void changeModality(List<AppointmentBean> appointmentsToAdd){
       boolean hasChanged=appointmentPs.changeModality(appointmentsToAdd);
         if(hasChanged){
              user.setInPerson(true);
              user.setOnline(true);
              Printer.println("Modalità cambiata con successo");
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


