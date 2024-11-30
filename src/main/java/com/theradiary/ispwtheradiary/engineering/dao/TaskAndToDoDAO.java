package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.TaskAndToDoQuery;
import com.theradiary.ispwtheradiary.model.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TaskAndToDoDAO {
    private TaskAndToDoDAO(){}
    public static void Diary(Patient patient, String diaryContent) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            TaskAndToDoQuery.Diary(conn, diaryContent, patient.getCredentials().getMail());
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
}
