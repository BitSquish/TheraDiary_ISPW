package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryAndMajorDAO {
    public static void saveSelectedCategories(List<Category> categories,String patientName) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            Account.saveSelectedCategories(conn, categories,patientName);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void saveSelectedMajor(List<Major> majors,String psychologistName) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            Account.saveSelectedMajor(conn, majors,psychologistName);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
