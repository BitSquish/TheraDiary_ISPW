package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.AccountQuery;
import com.theradiary.ispwtheradiary.model.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryAndMajorDAO {
    public static void addCategory(Patient patient) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            ResultSet rs=AccountQuery.addCategory(conn,patient.getCategories());
            if(rs.next()){
                patient.addCategory(Category.valueOf(rs.getString("category")));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static void saveSelectedMajors(List<Major> majors,String psychologistName) {
        try(Connection conn= ConnectionFactory.getConnection()) {
            AccountQuery.saveSelectedMajor(conn, majors,psychologistName);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
