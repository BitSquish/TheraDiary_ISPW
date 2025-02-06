package com.theradiary.ispwtheradiary.dao.demo;

import com.theradiary.ispwtheradiary.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;

import java.time.LocalDate;
import java.util.*;

public class TaskAndToDoDAOInMemory implements TaskAndToDoDAO {

    //Strutture dati in memoria
    /**********************************************************Diario*****************************************************************************************/
    @Override
    public void diary(Patient patient, String diaryContent, LocalDate selectedDate) { //aggiungo un diario
        SharedResources.getInstance().getDiaryTable()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> new HashMap<>())
                .put(selectedDate, diaryContent); // computeIfAbsent: se la chiave non è presente, la inserisce con il valore restituito dalla lambda
    }

    @Override
    public Optional<String> getDiaryForToday(Patient patient) {
        return getDiaryEntry(LocalDate.now(), patient);
    }

    @Override
    public Optional<String> getDiaryEntry(LocalDate selectedDate, Patient patient) {
        Map<LocalDate, String> diary = SharedResources.getInstance().getDiaryTable()
                .get(patient.getCredentials().getMail()); //recupero il diario del paziente
        if (diary == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(diary.get(selectedDate)); //restituisco il contenuto del diario per la data selezionata
    }
    @Override
    public void removeDiaryEntry(LocalDate selectedDate, Patient patient) {
        Map<LocalDate, String> diary = SharedResources.getInstance().getDiaryTable()
                .get(patient.getCredentials().getMail());
        if (diary != null) {
            diary.remove(selectedDate);
        }
    }

    /*************************************************to do***********************************************************************/
    @Override
    public void saveToDo(Patient patient, ToDoItem toDoItem) {
        SharedResources.getInstance().getToDoTable()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> new ArrayList<>()).add(toDoItem);
    }

    @Override
    public List<ToDoItem> retriveToDoList(Patient patient) {
        return SharedResources.getInstance().getToDoTable()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> new ArrayList<>());
    }

    @Override
    public void deleteToDoItem(Patient patient, ToDoItem toDoItem) {
        List<ToDoItem> patientToDo = SharedResources.getInstance().getToDoTable()
                .get(patient.getCredentials().getMail());
        if (patientToDo != null) {
            for (int i = 0; i < patientToDo.size(); i++) {
                ToDoItem currentToDo = patientToDo.get(i);
                if (currentToDo.getToDo().equals(toDoItem.getToDo())) {
                    patientToDo.remove(i);
                    break;
                }
            }
        }
    }

    /*************************************************task***********************************************************************/
    @Override
    public void saveTask(Patient patient, Task task) {
        SharedResources.getInstance().getTaskTable()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> new ArrayList<>()).add(task);
    }

    @Override
    public void deleteTask(Patient patient, Task task) {
        List<Task> patientTask = SharedResources.getInstance().getTaskTable()
                .get(patient.getCredentials().getMail());
        if (patientTask != null) {
            for (int i = 0; i < patientTask.size(); i++) {
                Task currentTask = patientTask.get(i);
                if (currentTask.getTaskName().equals(task.getTaskName()) || currentTask.getTaskDeadline().equals(task.getTaskDeadline())) {
                    patientTask.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public void updateTask(Patient patient, Task task) {
        List<Task> updatedList= SharedResources.getInstance().getTaskTable().get(patient.getCredentials().getMail());
        if(updatedList!=null){
            for(int i=0; i<updatedList.size(); i++){
                Task currentTask= updatedList.get(i);
                if(currentTask.getTaskName().equals(task.getTaskName()) || currentTask.getTaskDeadline().equals(task.getTaskDeadline())){
                    updatedList.set(i, task);
                    break;
                }
            }
        }
    }

    @Override
    public List<Task> retrieveTasks(Patient patient) {
        return SharedResources.getInstance().getTaskTable()
                .computeIfAbsent(patient.getCredentials().getMail(), k -> new ArrayList<>());
    }
}
