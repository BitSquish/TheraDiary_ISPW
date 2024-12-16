package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;

import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskPsychologistCLI extends AbstractState {
    protected PatientBean selectedPatient;
    protected PsychologistBean user;
    StateMachineImpl context;
    public TaskPsychologistCLI(PsychologistBean user,PatientBean selectedPatient) {
        this.selectedPatient = selectedPatient;
        this.user = user;
    }
    Scanner scanner = new Scanner(System.in);

    @Override
    public void action(StateMachineImpl context) {
        int scelta;
        while ((scelta = scanner.nextInt()) != 0) {
            try {
                switch (scelta) {
                    case (1):
                        showDiary();
                        break;
                    case (2):
                        showToDoList();
                        break;
                    case (3):
                        goNext(context,new ToDoCLI(user,selectedPatient));
                        break;
                    case (4):
                        viewTasks();
                        break;
                    case (5):
                        goNext(context,new TaskCLI(user,selectedPatient));
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
        goNext(context, new HomePsychologistCLI(user));

    }
    private void showDiary() {
        //mostra il diario
        Printer.printlnBlue("-------------------Diario di"+ selectedPatient.getFullName()+"-------------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate= LocalDate.now().format(formatter);
        Printer.println("Data:" + formattedDate);
        String diary = TaskAndToDo.getDiaryForToday(selectedPatient);
        if (diary.isEmpty()) {
            Printer.println("Il diario è vuoto");
        } else {
            Printer.println(diary);
        }
    }

    private void showToDoList() {
        //mostra la lista delle cose da fare
        Printer.printlnBlue("-------------------Lista cose da fare-------------------");
        Printer.println("Lista cose da fare di " + selectedPatient.getFullName() + ":");
        TaskAndToDo.toDoList(selectedPatient);
        List<ToDoItemBean> toDoList = selectedPatient.getToDoList();
        //Nell todoitembean ho creato un metodo tostring che mi restituisce la stringa da stampare
        if(toDoList.isEmpty()){
            Printer.println("Cose da fare completate o non presenti");
            goNext(context,new ToDoCLI(user,selectedPatient));
        }else{
            for(int i=0;i<toDoList.size();i++){
                Printer.println((i+1)+". "+toDoList.get(i));
            }
        }
    }

    private void viewTasks(){
        Printer.printlnBlue("-------------------Lista task-------------------");
        Printer.println("Lista task di "+selectedPatient.getFullName()+":");
        TaskAndToDo.retrieveTasks(selectedPatient);
        List<TaskBean> taskList = selectedPatient.getTasks();
        if(taskList.isEmpty()){
            Printer.println("Non ci sono task");
            goNext(context,new TaskCLI(user,selectedPatient));
        }else{
            for(int i=0;i<taskList.size();i++){
                Printer.println((i+1)+". "+taskList.get(i));
            }
        }
    }
    /*-------------------Metodi di AbstractState-------------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza diario");
        Printer.println("2.Visualizza lista cose da fare");
        Printer.println("3.Modifica lista cose da fare");
        Printer.println("4.Visualizza lista task");
        Printer.println("5.Modifica lista task");
        Printer.println("0.Indietro");
        Printer.print("Scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella pagina dei task -------------------");
        Printer.println("Ciao " + user.getFullName() + ",scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }



}
