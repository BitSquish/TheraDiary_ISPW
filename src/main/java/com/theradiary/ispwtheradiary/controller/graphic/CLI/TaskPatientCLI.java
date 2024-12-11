package com.theradiary.ispwtheradiary.controller.graphic.CLI;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskPatientCLI extends AbstractState {
    protected PatientBean patientBean;
    public TaskPatientCLI(PatientBean patientBean) {
        this.patientBean = patientBean;
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
                        addToDiary(scanner);
                        break;
                    case (4):
                        completeToDo(scanner);
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
        goNext(context, new HomePatientCLI(patientBean));
    }

    //metodi
    private void showDiary() {
        //mostra il diario
        Printer.printlnBlue("-------------------Diario-------------------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate= LocalDate.now().format(formatter);
        Printer.println("Data:" + formattedDate);
        Printer.println("Diario di " + patientBean.getFullName() + ":");
        String diary = TaskAndToDo.getDiaryForToday(patientBean);
        if (diary.isEmpty()) {
            Printer.println("Il diario è vuoto");
            addToDiary(scanner);
        } else {
            Printer.println(diary);
        }
    }

    private void showToDoList() {
        //mostra la lista delle cose da fare
        Printer.printlnBlue("-------------------Lista  cose da fare-------------------");
        Printer.println("Lista  cose da fare di " + patientBean.getFullName() + ":");

        TaskAndToDo.toDoList(patientBean);
        List<ToDoItemBean> toDoList = patientBean.getToDoList();

        if (toDoList.isEmpty()) {
            Printer.println("Non hai nulla da completare!");
        } else {
            for (int i = 0; i < toDoList.size(); i++) {
                Printer.println((i + 1) + ". " + toDoList.get(i));
            }
        }
    }
    private void addToDiary(Scanner scanner) {
        //scrivi diario
        Printer.println("Scrivi la tua pagina di diario:");
        StringBuilder diaryEntry = new StringBuilder();//concateno le stringhe
        //Leggo le righe fino a quando l'utente non insrisce una riga vuota o fine
        String line;
        while(true){
            line= scanner.nextLine();//leggo una nuova riga
            if(line.isEmpty()){
                break;//termina l'inserimento
            }
            //Aggiungo la riga al diario
            diaryEntry.append(line).append("\n");
        }


        TaskAndToDo.saveDiary(diaryEntry.toString(), patientBean);
        Printer.println(diaryEntry.toString());
        patientBean.setDiary(diaryEntry.toString());
        Printer.println("Voce aggiunta al diario");
    }
    private void completeToDo(Scanner scanner) {
        showToDoList();
        //completa un'attività
        Printer.println("Inserisci il numero dell'attività completata:");
        int toDoIndex = scanner.nextInt();
        List<ToDoItemBean> toDoList = patientBean.getToDoList();
        if (toDoIndex < 1 || toDoIndex > toDoList.size()) {
            Printer.errorPrint("Indice non valido");
        } else {
            ToDoItemBean toDoItem = toDoList.get(toDoIndex - 1);
            toDoItem.setCompleted(true);
            TaskAndToDo.saveToDoList((ObservableList<ToDoItemBean>) toDoList, patientBean);
            Printer.println("Attività completata");
        }
    }
    /*--------------------------Metodi di AbstractState--------------------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza il diario");
        Printer.println("2.Visualizza la lista delle cose da fare");
        Printer.println("3.Aggiungi voce al diario");
        Printer.println("4.Completa attività");
        Printer.println("0.Torna indietro");
        Printer.print("Opzione scelta:");
    }
    @Override
    public void stampa() {
        Printer.println(" ");
        Printer.printlnBlue("-------------------Benvenuto nella pagine dei task-------------------");
        Printer.println("Ciao" + patientBean.getFullName() + ",scegli cosa vuoi fare:");
    }
    @Override
    public void enter(StateMachineImpl context) {
        stampa();
        showMenu();
    }




}
