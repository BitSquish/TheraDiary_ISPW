package com.theradiary.ispwtheradiary.controller.graphic.comandline;

import com.theradiary.ispwtheradiary.controller.application.TaskAndToDo;
import com.theradiary.ispwtheradiary.engineering.others.Printer;

import com.theradiary.ispwtheradiary.engineering.others.beans.PatientBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.TaskBean;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.engineering.patterns.state.AbstractState;
import com.theradiary.ispwtheradiary.engineering.patterns.state.StateMachineImpl;


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
                    case(5):
                        viewTasks();
                        break;
                    case(6):
                        doTask(scanner);
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
        Printer.printlnBlue("Diario del :" + formattedDate );


        TaskAndToDo.getDiaryForToday(patientBean);
        String diary=patientBean.getDiary();

        if (diary.isEmpty()) {
            Printer.println("Il diario è vuoto");
            addToDiary(scanner);
        } else {
            Printer.println(diary);
        }
    }
    private void addToDiary(Scanner scanner){
        if(hasDiaryForToday()) {
            Printer.printlnGreen("Hai già scritto il diario per oggi");
            return;
        }
        String diaryEntry=collectDiaryEntry(scanner);
        if(diaryEntry.isEmpty()){
            Printer.errorPrint("Diario vuoto");
            return;
        }
        saveDiaryEntry(diaryEntry);
    }
    //metodo per controllare se il paziente ha già scritto il diario per oggi
    private boolean hasDiaryForToday(){
        TaskAndToDo.getDiaryForToday(patientBean);
        if(patientBean.getDiary().isEmpty()){
            return false;
        }
        return true;
    }
    //metodo per raccogliere l'input del paziente
    private String collectDiaryEntry(Scanner scanner){
        Printer.println("Scrivi la tua pagina di diario (massimo 500 parole). Digita 'FINE' per terminare:");
        StringBuilder diaryEntryBuilder = new StringBuilder();
        int wordCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //controllo per uscire se l'utente digita "FINE"
            if (line.trim().equalsIgnoreCase("FINE")) {
                break;
            }
            //Calcolo il numero di parole nella linea corrente
            String[] words = line.trim().split("\\s+");
            wordCount += words.length;
            //Controllo che il numero di parole non superi le 500
            if (wordCount > 500) {
                Printer.errorPrint("Hai superato il limite di 500 parole");
                return "";
            }
            //Aggiungo la linea al diario
            diaryEntryBuilder.append(line).append(System.lineSeparator());
        }
        //Costruisco la stringa completa
        return diaryEntryBuilder.toString().trim();
    }
    //metodo per salvare l'input del paziente
    private void saveDiaryEntry(String diaryEntry){
        try {
            TaskAndToDo.saveDiary(diaryEntry, patientBean, LocalDate.now());
            Printer.printlnGreen("Voce aggiunta al diario");
        } catch (Exception e) {
            Printer.errorPrint("Errore nel salvataggio del diario");
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
    private void completeToDo(Scanner scanner) {
        showToDoList();
        Printer.println("Inserisci il numero dell'attività completata:");
        int index = scanner.nextInt();
        for(int i=0;i<patientBean.getToDoList().size();i++){
            if(i==index-1){
                patientBean.getToDoList().get(i).setCompleted(true);
                TaskAndToDo.deleteToDo(patientBean.getToDoList().get(i),patientBean);
                Printer.printlnGreen("Attività completata");
            }

        }

    }
    private void viewTasks(){
        Printer.printlnBlue("-------------------Ciao"+" "+ patientBean.getFullName()+","+"la tua lista task-------------------");
        TaskAndToDo.retrieveTasks(patientBean);
        List<TaskBean> taskList = patientBean.getTasks();
        if(taskList.isEmpty()){
            Printer.println("Non ci sono task da completare");
        }else{
            for(int i=0;i<taskList.size();i++){
                Printer.println((i+1)+". "+taskList.get(i));
            }
        }
    }
    private void doTask(Scanner scanner){
        viewTasks();
        Printer.println("Inserisci la posizione dell'elemento da completare");
        List<TaskBean> taskList = patientBean.getTasks();
        try {
            int position = scanner.nextInt();
            if(position>0 && position<=taskList.size()){
                Printer.print("Inserisci la nuova descrizione: ");
                String status = scanner.nextLine();
                TaskBean taskBean = taskList.get(position-1);
                taskBean.setTaskStatus(status);
                TaskAndToDo.updateTasks(patientBean,taskBean);
                Printer.printlnGreen("Elemento completato!");
            }else{
                Printer.errorPrint("Scelta non valida");
            }
        }catch (Exception e){
            Printer.errorPrint("Errore nella selezione");
            scanner.nextLine();
        }
    }

    /*--------------------------Metodi di AbstractState--------------------------*/
    @Override
    public void showMenu() {
        Printer.println("1.Visualizza il diario");
        Printer.println("2.Visualizza la lista delle cose da fare");
        Printer.println("3.Aggiungi voce al diario");
        Printer.println("4.Completa attività");
        Printer.println("5.Visualizza lista task");
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
