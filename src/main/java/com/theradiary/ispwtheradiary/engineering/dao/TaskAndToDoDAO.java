package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskAndToDoDAO {
    //Metodi per la gestione del diario
    void diary(Patient patient, String diaryContent, LocalDate selectedDate);

    Optional<String> getDiaryForToday(Patient patient);
    void removeDiaryEntry(LocalDate selectedDate, Patient patient);

    Optional<String> getDiaryEntry(LocalDate selectedDate, Patient patient);
    //Metodi per la gestione to-do list
    void saveToDo(Patient patient, ToDoItem toDoItem);

    List<ToDoItem> retriveToDoList(Patient patient);

    void deleteToDoItem(Patient patient, ToDoItem toDoItem);
    //Metodi per la gestione dei task
    void saveTask(Patient patient, Task task);

    void deleteTask(Patient patient, Task task);

    void updateTask(Patient patient, Task task);

    List<Task> retrieveTasks(Patient patient);


}



