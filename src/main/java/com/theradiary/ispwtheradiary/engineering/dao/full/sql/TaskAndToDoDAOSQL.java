package com.theradiary.ispwtheradiary.engineering.dao.full.sql;

import com.theradiary.ispwtheradiary.engineering.dao.TaskAndToDoDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.TaskAndToDoQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskAndToDoDAOSQL implements TaskAndToDoDAO {
    private static final String CONTENT = "contenuto";
    /**************diario***************/
    @Override
    public void diary(Patient patient, String diaryContent, LocalDate selectedDate) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.diary(conn, diaryContent, patient.getCredentials().getMail(), selectedDate);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel salvataggio del diario",e);
        }
    }

    @Override
    public Optional<String> getDiaryForToday(Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getDiaryForToday(conn, patient.getCredentials().getMail());
            if(rs.next()){
                patient.setDiary(rs.getString(CONTENT));
                return Optional.of(rs.getString(CONTENT));
            }else {
                return Optional.empty();
            }
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero del diario",e);
        }

    }
    @Override
    public Optional<String> getDiaryEntry(LocalDate selectedDate,Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getDiaryEntry(conn, selectedDate, patient.getCredentials().getMail());
            if(rs.next()){
                return Optional.of(rs.getString(CONTENT));
            }else {
                return Optional.empty();
            }
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero del diario",e);
        }
    }

    /**************to do***************/
    @Override
    public void saveToDo(Patient patient, ToDoItem toDoItem) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.saveToDoItem(conn, patient.getCredentials().getMail(), toDoItem);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel salvataggio del to do",e);
        }
    }


    @Override
    public List<ToDoItem> retriveToDoList(Patient patient) {
        List<ToDoItem> toDoItems=new ArrayList<>();
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getToDoList(conn, patient.getCredentials().getMail());
            while(rs.next()){
                toDoItems.add(new ToDoItem(rs.getString("description"),rs.getBoolean("done")));
            }
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero del to do",e);
        }
        return toDoItems;
    }
    @Override
    public void deleteToDoItem(Patient patient, ToDoItem toDoItem) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.deleteToDoItem(conn, patient.getCredentials().getMail(), toDoItem);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nella cancellazione del to do",e);
        }
    }
    /*********************************task**********************************/
    @Override
    public void saveTask(Patient patient, Task task) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.saveTask(conn, patient.getCredentials().getMail(), task);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel salvataggio del task",e);
        }
    }
    @Override
    public void deleteTask(Patient patient, Task task) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.deleteTask(conn, patient.getCredentials().getMail(), task);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nella cancellazione del task",e);
        }
    }
    @Override
    public void updateTask(Patient patient, Task task) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.updateTask(conn, patient.getCredentials().getMail(), task);
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nell'aggiornamento del task",e);
        }
    }
    @Override
    public List<Task> retrieveTasks(Patient patient) {
        List<Task> task=new ArrayList<>();
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.retrieveTasks(conn, patient.getCredentials().getMail());
            while(rs.next()){
                task.add(new Task(rs.getString("description"),rs.getDate("deadline").toLocalDate(),rs.getString("status")));
            }
        }catch (SQLException e){
            throw new PersistenceOperationException("Errore nel recupero dei task",e);
        }
        return task;
    }
}
