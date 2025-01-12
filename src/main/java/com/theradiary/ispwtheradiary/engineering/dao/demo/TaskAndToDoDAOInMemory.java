package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskAndToDoDAOInMemory implements TaskAndToDoDAO {
    @Override
    public void diary(Patient patient, String diaryContent, LocalDate selectedDate) {

    }

    @Override
    public Optional<String> getDiaryForToday(Patient patient) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getDiaryEntry(LocalDate selectedDate, Patient patient) {
        return Optional.empty();
    }

    @Override
    public void saveToDo(Patient patient, ToDoItem toDoItem) {

    }

    @Override
    public List<ToDoItem> retriveToDoList(Patient patient) {
        return null;
    }

    @Override
    public void deleteToDoItem(Patient patient, ToDoItem toDoItem) {

    }

    @Override
    public void saveTask(Patient patient, Task task) {

    }

    @Override
    public void deleteTask(Patient patient, Task task) {

    }

    @Override
    public void updateTask(Patient patient, Task task) {

    }

    @Override
    public List<Task> retrieveTasks(Patient patient) {
        return null;
    }
    //TODO
}
