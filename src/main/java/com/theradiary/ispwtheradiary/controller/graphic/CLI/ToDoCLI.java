package com.theradiary.ispwtheradiary.controller.graphic.CLI;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class ToDoCLI extends AbstractState {
    protected PatientBean selectedPatient;
    protected PsychologistBean user;
    Scanner scanner = new Scanner(System.in);
    public ToDoCLI(PsychologistBean user, PatientBean selectedPatient) {
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
                        addNewToDo(scanner);
                        break;
                    case (2):
                        modifyToDo(scanner, selectedPatient.getToDoList());
                        break;
                    case (3):
                        deleteToDoItem(scanner, selectedPatient.getToDoList());
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
    private void addNewToDo(Scanner scanner) {
        Printer.print("Inserisci la descrizione del nuovo ToDo: ");
        String description = scanner.nextLine();
        ToDoItemBean newToDo = new ToDoItemBean(description,false);
        TaskAndToDo.saveToDo(newToDo,selectedPatient);
        Printer.printlnGreen("Elemento aggiunto correttamente");
    }
    private void deleteToDoItem(Scanner scanner, List<ToDoItemBean> toDoList){
        Printer.println("Seleziona l'elemento da eliminare:");
        int position = -1;
        for (int i = 0; i < toDoList.size(); i++) {
            Printer.println((i + 1) + ". " + toDoList.get(i).isCompleted() + " " + toDoList.get(i).getToDo());
        }
        try{
            position = scanner.nextInt();
            if(position>0 && position<=toDoList.size()){
                TaskAndToDo.deleteToDo(toDoList.get(position-1),selectedPatient);
                Printer.printlnGreen("Elemento eliminato correttamente");
            }else{
                Printer.errorPrint("Scelta non valida");
            }
        }catch (Exception e){
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    private void modifyToDo(Scanner scanner, List<ToDoItemBean> toDoList){
        Printer.println("Seleziona l'elemento da modificare:");
        int position = -1;
        for (int i = 0; i < toDoList.size(); i++) {
            Printer.println((i + 1) + ". " + toDoList.get(i).isCompleted() + " " + toDoList.get(i).getToDo());
        }
        try{
            position = scanner.nextInt();
            if(position>0 && position<=toDoList.size()){
                Printer.print("Inserisci la nuova descrizione: ");
                String newDescription = scanner.nextLine();
                ToDoItemBean toDo = toDoList.get(position-1);
                toDo.setToDo(newDescription);
                TaskAndToDo.saveToDo(toDo,selectedPatient);
                Printer.printlnGreen("Elemento modificato correttamente");
            }else{
                Printer.errorPrint("Scelta non valida");
            }
        }catch (Exception e){
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    @Override
    public void showMenu() {
        Printer.println("1. Aggiungi elemento");
        Printer.println("2. Modifica elemento");
        Printer.println("3. Elimina elemento");
        Printer.println("0. Indietro");
        Printer.print("Scelta: ");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella pagina dei ToDo-------------------");
        Printer.println("Ciao " + user.getFullName() + ",scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }





}