package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.TaskAndToDoQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;
import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskAndToDoDAO {
    private TaskAndToDoDAO(){}
    /**************diario***************/
    public static void Diary(Patient patient, String diaryContent, LocalDate selectedDate) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.Diary(conn, diaryContent, patient.getCredentials().getMail(), selectedDate);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public static Optional<String> getDiaryForToday(Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getDiaryForToday(conn, patient.getCredentials().getMail());
            if(rs.next()){
                patient.setDiary(rs.getString("contenuto"));
                return Optional.of(rs.getString("contenuto"));
            }else {
                return Optional.empty();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public static Optional<String> getDiaryEntry(LocalDate selectedDate,Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getDiaryEntry(conn, selectedDate, patient.getCredentials().getMail());
            if(rs.next()){
                return Optional.of(rs.getString("contenuto"));
            }else {
                return Optional.empty();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**************to do***************/
    public static void saveToDo(Patient patient, ToDoItem toDoItem) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.saveToDoItem(conn, patient.getCredentials().getMail(), toDoItem);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public static List<ToDoItem> retriveToDoList(Patient patient) {
        List<ToDoItem> toDoItems=new ArrayList<>();
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getToDoList(conn, patient.getCredentials().getMail());
            while(rs.next()){
                toDoItems.add(new ToDoItem(rs.getString("description"),rs.getBoolean("done")));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return toDoItems;
    }

    public static void deleteToDoItem(Patient patient, ToDoItem toDoItem) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.deleteToDoItem(conn, patient.getCredentials().getMail(), toDoItem);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    /*********************************task**********************************/
    public static List<Task> retriveTasks(Patient patient) {
        List<Task> tasks=new ArrayList<>();
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=TaskAndToDoQuery.getTasks(conn, patient.getCredentials().getMail());
            while(rs.next()){
                tasks.add(new Task(rs.getString("description"),rs.getDate("deadline").toLocalDate(),rs.getString("status")));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public static void saveTask(Patient patient, Task task) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.saveTask(conn, patient.getCredentials().getMail(), task);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void deleteTask(Patient patient, Task task) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.deleteTask(conn, patient.getCredentials().getMail(), task);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
