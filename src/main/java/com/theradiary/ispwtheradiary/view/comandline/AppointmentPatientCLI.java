package com.theradiary.ispwtheradiary.view.comandline;


import com.theradiary.ispwtheradiary.controller.AppointmentPtController;
import com.theradiary.ispwtheradiary.engineering.enums.DayOfTheWeek;
import com.theradiary.ispwtheradiary.engineering.enums.TimeSlot;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.beans.AppointmentBean;
import com.theradiary.ispwtheradiary.beans.PatientBean;
import com.theradiary.ispwtheradiary.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.patterns.state.StateMachineImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class AppointmentPatientCLI extends AbstractState {
    protected PsychologistBean psychologistBean;
    private final Scanner scanner = new Scanner(System.in);
    protected PatientBean user;
    private final List<AppointmentBean> allAppointments = new ArrayList<>();
    private final AppointmentPtController appointmentController = new AppointmentPtController();
    private static final String PRESENZA="In presenza";
    private static final String ONLINE="Online";
    public AppointmentPatientCLI(PatientBean user){
        this.psychologistBean = user.getPsychologistBean();
        this.user=user;
        loadAppointments();

    }
    @Override
    public void action(StateMachineImpl context) {
        if(psychologistBean.getCredentialsBean().getMail()==null){
            Printer.println("Non hai uno psicologo associato. Vuoi cercarne uno? (s/n)");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("s")){
                goNext(context,new SearchCLI(user));
            }else{
                goNext(context,new HomePatientCLI(user));
            }
        }else if(allAppointments.isEmpty()) {
            Printer.println(("Lo psicologo non ha appuntamenti disponibili"));
            Printer.println("Contatta lo psicologo per fissare un appuntamento : " + psychologistBean.getCredentialsBean().getMail());
        }else{
            AppointmentBean existingAppointment=appointmentController.getAppointmentIfExists(user,allAppointments);
            if(existingAppointment!=null){
                Printer.println("Hai già un appuntamento fissato con lo psicologo");
                Printer.println("Giorno: "+DayOfTheWeek.translateDay(existingAppointment.getDay().getId()));
                Printer.println("Fascia oraria: "+TimeSlot.translateTimeSlot(existingAppointment.getTimeSlot().getId()));
                Printer.println("Contatta lo psicologo per eventuali modifiche: "+psychologistBean.getCredentialsBean().getMail());
            }else{
                showAvailableAppointments();
            }

        }


    }
    private void showAvailableAppointments() {
        Printer.println("Appuntamenti disponibili:");
        List<String> days = getDistinctDays();

        if (days.isEmpty()) {
            Printer.println("Nessun appuntamento disponibile");
            return;
        }

        displayOptions("Giorni disponibili:", days);
        String selectedDay = getUserSelection("Seleziona un giorno:", days);

        List<String> timeSlots = getTimeSlotsForDay(selectedDay);
        displayOptions("Fasce orarie disponibili:", timeSlots);
        String selectedTimeSlot = getUserSelection("Seleziona una fascia oraria:", timeSlots);

        List<String> modalities = getModalitiesForDayAndTime(selectedDay, selectedTimeSlot);
        displayOptions("Modalità disponibili:", modalities);
        String selectedModality = getUserSelection("Seleziona una modalità:", modalities);

        confirmAppointment(selectedDay, selectedTimeSlot, selectedModality);
        Printer.println("Appuntamento fissato con successo");
    }

    private List<String> getDistinctDays() {
        return allAppointments.stream()
                .map(a -> DayOfTheWeek.translateDay(a.getDay().getId()))
                .distinct()
                .toList();
    }

    private List<String> getTimeSlotsForDay(String day) {
        return allAppointments.stream()
                .filter(appointment -> DayOfTheWeek.translateDay(appointment.getDay().getId()).equals(day))
                .map(appointment -> TimeSlot.translateTimeSlot(appointment.getTimeSlot().getId()))
                .distinct()
                .toList();
    }

    private List<String> getModalitiesForDayAndTime(String day, String timeSlot) {
        return allAppointments.stream()
                .filter(appointment -> DayOfTheWeek.translateDay(appointment.getDay().getId()).equals(day)
                        && TimeSlot.translateTimeSlot(appointment.getTimeSlot().getId()).equals(timeSlot))
                .flatMap(appointment -> {
                    boolean inPerson = appointment.isInPerson();
                    boolean online = appointment.isOnline();
                    if (inPerson && online) return Stream.of(PRESENZA, ONLINE);
                    else if (inPerson) return Stream.of(PRESENZA);
                    else if (online) return Stream.of(ONLINE);
                    else return Stream.empty();
                })
                .distinct()
                .toList();
    }

    private void displayOptions(String message, List<String> options) {
        Printer.println(message);
        for (int i = 0; i < options.size(); i++) {
            Printer.println((i + 1) + ". " + options.get(i));
        }
    }

    private String getUserSelection(String prompt, List<String> options) {
        Printer.println(prompt);
        int choice = Integer.parseInt(scanner.nextLine()) - 1;

        if (choice < 0 || choice >= options.size()) {
            Printer.errorPrint(SCELTA_NON_VALIDA);
            throw new IllegalArgumentException(SCELTA_NON_VALIDA);
        }
        return options.get(choice);
    }

    private void confirmAppointment(String selectedDay, String selectedTimeSlot, String selectedModality) {
        boolean inPerson = selectedModality.equals(PRESENZA);
        boolean online = !inPerson;

        DayOfTheWeek day = DayOfTheWeek.fromStringToDay(selectedDay);
        TimeSlot timeSlot = TimeSlot.fromStringToTimeSlot(selectedTimeSlot);

        AppointmentBean newAppointment = new AppointmentBean(psychologistBean, day, timeSlot, inPerson, online);
        newAppointment.setAvailable(false);
        newAppointment.setPatientBean(user.getCredentialsBean().getMail());
        appointmentController.askForAnAppointment(newAppointment);
    }
    private void loadAppointments(){
        appointmentController.loadAllAppointments(allAppointments,psychologistBean);
    }



    @Override
    public void enter(StateMachineImpl context) {
        Printer.println("Benvenuto "+user.getCredentialsBean().getMail());
    }



}


