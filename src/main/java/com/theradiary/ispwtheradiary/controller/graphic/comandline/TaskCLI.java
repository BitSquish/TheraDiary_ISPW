package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskCLI extends AbstractState {
    TaskAndToDoController taskAndToDoController = new TaskAndToDoController();
    protected PatientBean selectedPatient;
    protected PsychologistBean user;
    Scanner scanner = new Scanner(System.in);
    public TaskCLI(PsychologistBean user, PatientBean selectedPatient) {
        this.selectedPatient = selectedPatient;
        this.user = user;
    }
    @Override
    public void action(StateMachineImpl context){
        int scelta;
        while((scelta=scanner.nextInt())!=0) {
            try {
                switch (scelta) {
                    case (1):
                        addNewTask(scanner);
                        break;
                    case (2):
                        modifyTask(scanner);
                        break;
                    case (3):
                        deleteTask(scanner);
                        break;
                    default:
                        Printer.errorPrint("Scelta non valida");
                        break;
                }
            } catch (Exception e) {
                Printer.errorPrint("Errore nella scelta");
                scanner.nextLine();
            }
        }
        goNext(context, new TaskPsychologistCLI(user,selectedPatient));

    }
    private void addNewTask(Scanner scanner) {
        Printer.print("Inserisci la descrizione del nuovo Task: ");
        scanner.nextLine();
        String description = scanner.nextLine();
        Printer.print("Inserisci la data di scadenza del Task (formato gg/mm/aaaa): ");
        try{
            String date = scanner.nextLine();
            TaskBean newTask = new TaskBean(description, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")), "non completato");
            taskAndToDoController.saveTasks(selectedPatient, newTask);
            Printer.printlnGreen("Elemento aggiunto correttamente");
        }catch (Exception e){
            Printer.errorPrint("Errore nella data");

        }
    }
    private void deleteTask(Scanner scanner) {
        taskAndToDoController.retrieveTasks(selectedPatient);
        List<TaskBean> taskList = selectedPatient.getTasks();
        printTasks(taskList);
        Printer.println("Seleziona l'elemento da eliminare:");
        try {
            int position = scanner.nextInt();
            scanner.nextLine();
            if (position > 0 && position <= taskList.size()) {
                taskAndToDoController.deleteTask(taskList.get(position - 1), selectedPatient);
                Printer.printlnGreen("Elemento eliminato correttamente");
            } else {
                Printer.errorPrint("Scelta non valida");
            }
        } catch (Exception e) {
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    private void modifyTask(Scanner scanner) {
        taskAndToDoController.retrieveTasks(selectedPatient);
        List<TaskBean> tasks = selectedPatient.getTasks();
        printTasks(tasks);
        Printer.println("Inserisci la posizione dell'elemento da modificare");
        try {
            int position = scanner.nextInt();
            scanner.nextLine();
            if (position > 0 && position <= selectedPatient.getTasks().size()) {
                Printer.println("Inserisci la nuova descrizione");
                String newDescription = scanner.nextLine();
                Printer.println("Inserisci la nuova data di scadenza");
                LocalDate newDeadline = LocalDate.parse(scanner.nextLine());
                tasks.get(position - 1).setTaskName(newDescription);
                tasks.get(position - 1).setTaskDeadline(String.valueOf(newDeadline));
                taskAndToDoController.saveTasks(selectedPatient, tasks.get(position - 1));
                Printer.println("Elemento modificato");
            } else {
                Printer.errorPrint("Posizione non valida");
            }
        } catch (Exception e) {
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    private void printTasks(List<TaskBean> taskList) {
        if (taskList.isEmpty()) {
            Printer.println("Non ci sono task da completare");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                Printer.println((i + 1) + ". " + taskList.get(i));
            }
        }
    }
    @Override
    public void showMenu(){
        Printer.println("1. Aggiungi task");
        Printer.println("2. Modifica task");
        Printer.println("3. Elimina task");
        Printer.println("0. Indietro");
        Printer.print("Scelta: ");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella pagina dei Tasks-------------------");
        Printer.println("Ciao " + user.getFullName() + ", scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }
}
