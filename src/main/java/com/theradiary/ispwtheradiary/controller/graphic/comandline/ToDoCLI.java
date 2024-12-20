package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDoController;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.PsychologistBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;

import java.util.List;
import java.util.Scanner;

public class ToDoCLI extends AbstractState {
    TaskAndToDoController taskAndToDoController = new TaskAndToDoController();
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
                        modifyToDo(scanner);
                        break;
                    case (3):
                        deleteToDoItem(scanner);
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
        taskAndToDoController.saveToDo(newToDo,selectedPatient);
        Printer.printlnGreen("Elemento aggiunto correttamente");
    }
    private void deleteToDoItem(Scanner scanner){
        taskAndToDoController.toDoList(selectedPatient);
        List<ToDoItemBean> doList = selectedPatient.getToDoList();
        printToDoList(doList);
        Printer.println("Seleziona l'elemento da modificare:");
        try{
            int position = scanner.nextInt();
            scanner.nextLine();
            if(position>0 && position<=doList.size()){
                taskAndToDoController.deleteToDo(doList.get(position-1),selectedPatient);
                Printer.printlnGreen("Elemento eliminato correttamente");
            }else{
                Printer.errorPrint("Scelta non valida");
            }
        }catch (Exception e){
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    private void modifyToDo(Scanner scanner){
        taskAndToDoController.toDoList(selectedPatient);
        List<ToDoItemBean> toDoList = selectedPatient.getToDoList();
        printToDoList(toDoList);
        Printer.println("Seleziona l'elemento da modificare:");
        try{
            int position = scanner.nextInt();
            scanner.nextLine();
            if(position>0 && position<=toDoList.size()){
                Printer.print("Inserisci la nuova descrizione: ");
                String newDescription = scanner.nextLine();
                ToDoItemBean toDo = toDoList.get(position-1);
                toDo.setToDo(newDescription);
                taskAndToDoController.saveToDo(toDo,selectedPatient);
                Printer.printlnGreen("Elemento modificato correttamente");
            }else{
                Printer.errorPrint("Scelta non valida");
            }
        }catch (Exception e){
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }
    private void printToDoList(List<ToDoItemBean> toDoList){
        if(toDoList.isEmpty()){
            Printer.println("Non hai nulla da completare!");
        }else{
            for(int i=0;i<toDoList.size();i++){
                Printer.println((i+1)+". "+toDoList.get(i));
            }
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
