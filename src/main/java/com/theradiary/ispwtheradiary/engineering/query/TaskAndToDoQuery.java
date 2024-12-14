package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.others.beans.ToDoItemBean;
import com.theradiary.ispwtheradiary.model.Task;
import com.theradiary.ispwtheradiary.model.ToDoItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TaskAndToDoQuery {
    private TaskAndToDoQuery(){}
    /*-------------------------DIARIO-------------------------*/
    public static void Diary(Connection conn, String diary, String mail, LocalDate selectedDate) {
        System.out.println("Contenuto del diario da inserire: " + diary);
        String query= "INSERT INTO diary (contenuto,data_creazione,patient) VALUES (?,?,?)";
        try  {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, diary);
            pstmt.setDate(2, java.sql.Date.valueOf(selectedDate));
            pstmt.setString(3, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ResultSet getDiaryForToday(Connection conn, String mail) throws SQLException {
        String query = "SELECT contenuto FROM diary WHERE patient=? AND DATE(data_creazione)=CURDATE()";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1,mail);
        return pstmt.executeQuery();

    }

    public static ResultSet getDiaryEntry(Connection conn, LocalDate selectedDate, String mail) {
        String query = "SELECT contenuto FROM diary WHERE data_creazione=? AND patient=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, java.sql.Date.valueOf(selectedDate));
            pstmt.setString(2, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*-------------------------TODOLIST-------------------------*/

    public static ResultSet getToDoList(Connection conn, String mail) {
        String query = "SELECT description,done FROM todo WHERE patient=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void saveToDoItem(Connection conn, String mail, ToDoItem toDoItem)
    {
        String query = "INSERT INTO todo (description,done,patient) VALUES (?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, toDoItem.getToDo());
            pstmt.setBoolean(2, toDoItem.isCompleted());
            pstmt.setString(3, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteToDoItem(Connection conn, String mail, ToDoItem toDoItem) {
        String query = "DELETE FROM todo WHERE description=? AND patient=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, toDoItem.getToDo());
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*-------------------------TASK-------------------------*/

    public static ResultSet getTasks(Connection conn, String mail) {
        String query="SELECT description,deadline,status FROM task WHERE patient=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mail);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTask(Connection conn, String mail, Task task) {
        String query = "INSERT INTO task (description,deadline,status,patient) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, task.getTaskName());
            pstmt.setDate(2, java.sql.Date.valueOf(task.getTaskDeadline()));
            pstmt.setString(3, task.getTaskStatus());
            pstmt.setString(4, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void deleteTask(Connection conn, String mail, Task task) {
        String query = "DELETE FROM task WHERE description=? AND patient=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, mail);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}