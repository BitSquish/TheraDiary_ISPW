package com.theradiary.ispwtheradiary.controller.graphic.CLI;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;

import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;
import javafx.collections.ObservableList;

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
                        modifyToDO(scanner);
                        break;
                    case (4):
                        viewTasks();
                        break;
                    case (5):
                        modifyTask(scanner);
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
        goNext(context, new HomePsychologistCLI(selectedPatient.getPsychologistBean()));

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
            modifyToDO(scanner);
        }else{
            for(int i=0;i<toDoList.size();i++){
                Printer.println((i+1)+". "+toDoList.get(i));
            }
        }
    }

    private void modifyToDO(Scanner scanner) {
        TaskAndToDo.toDoList(selectedPatient);
        List<ToDoItemBean> toDoList = selectedPatient.getToDoList();

        if (toDoList.isEmpty()) {
            handleEmptyToDoList(scanner);
        } else {
            showToDoList();
            handleExistingToDoList(scanner, toDoList);
        }

    }

    private void handleEmptyToDoList(Scanner scanner) {
        Printer.println("Non ci sono cose da fare, crea un nuovo elemento");
        Printer.println("Inserisci la descrizione");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            Printer.errorPrint("La descrizione non può essere vuota");
            return;
        }

        ToDoItemBean toDoItemBean = new ToDoItemBean(description, false);
        TaskAndToDo.saveToDo(toDoItemBean, selectedPatient);
        Printer.printlnGreen("Elemento aggiunto");
    }
    private void handleExistingToDoList(Scanner scanner, List<ToDoItemBean> toDoList) {
        Printer.println("Vuoi inserire un nuovo elemento?[S/N]");
        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("s")) {
            addNewToDoItem(scanner);
        } else {
            Printer.println("Vuoi modificare un elemento?[S/N]");
            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("s")) {
                modifyToDoItem(scanner, toDoList);
            } else {
                goNext(context, new PatientListCLI(user));
            }
        }
    }
    private void addNewToDoItem(Scanner scanner) {
        Printer.println("Inserisci la descrizione");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            Printer.errorPrint("La descrizione non può essere vuota");
            return;
        }

        ToDoItemBean toDoItemBean = new ToDoItemBean(description, false);
        TaskAndToDo.saveToDo(toDoItemBean, selectedPatient);
        Printer.printlnGreen("Elemento aggiunto");
    }
    private void modifyToDoItem(Scanner scanner, List<ToDoItemBean> toDoList) {
        Printer.println("Inserisci la posizione dell'elemento da modificare");

        int position = -1;
        try {
            position = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            Printer.errorPrint("Posizione non valida");
            return;
        }

        if (position > 0 && position <= toDoList.size()) {
            Printer.println("Inserisci la nuova descrizione");
            String newDescription = scanner.nextLine().trim();

            if (newDescription.isEmpty()) {
                Printer.errorPrint("La descrizione non può essere vuota");
                return;
            }

            ToDoItemBean selectedToDo = toDoList.get(position - 1);
            selectedToDo.setToDo(newDescription);
            TaskAndToDo.saveToDo(selectedToDo, selectedPatient);
            Printer.printlnGreen("Elemento modificato");
        } else {
            Printer.errorPrint("Posizione non valida");
        }
    }
    private void viewTasks(){
        /*Printer.printlnBlue("-------------------Lista task-------------------");
        Printer.println("Lista task di "+patientBean.getFullName()+":");
        TaskAndToDo.taskList(patientBean);
        List<String> taskList = patientBean.getTaskListAsString();
        if(taskList.isEmpty()){
            Printer.println("Non ci sono task");
        }else{
            for(int i=0;i<taskList.size();i++){
                Printer.println((i+1)+". "+taskList.get(i));
            }
        }*/
    }
    private void modifyTask(Scanner scanner){
       /* viewTasks();
        Printer.println("Inserisci la posizione dell'elemento da modificare");
        int position=scanner.nextInt();
        List<ToDoItemBean> taskList = patientBean.getTaskList();
        if(position>0 && position<=patientBean.getTaskList().size()){
            Printer.println("Inserisci la nuova descrizione");
            String newDescription=scanner.nextLine();
            taskList.get(position-1).setToDo(newDescription);
            TaskAndToDo.saveTaskList((ObservableList<ToDoItemBean>) taskList,patientBean);
            Printer.println("Elemento modificato");
        }else{
            Printer.errorPrint("Posizione non valida");
        }*/
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
