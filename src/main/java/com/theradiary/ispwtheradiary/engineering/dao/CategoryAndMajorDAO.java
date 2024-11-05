package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.AccountQuery;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryAndMajorDAO {
    public static void addCategory(Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            AccountQuery.addCategory(conn,patient.getCategories(), patient.getMail());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void addMajor(Psychologist psychologist) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            AccountQuery.addMajor(conn,psychologist.getMajors(), psychologist.getMail());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
