package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Account {
    private Account(){}
    public static void saveSelectedCategories(Connection conn, List<Category> categories,String patientName)throws SQLException{
        String query= "INSERT INTO category (category_name,patient_name) VALUES (?,?)";
        try(PreparedStatement pstmt= conn.prepareStatement(query)){
            for(Category category: categories){
                pstmt.setString(1,category.name());
                pstmt.setString(2,patientName);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
    public static void saveSelectedMajor(Connection conn, List<Major> majors,String psychologistName)throws SQLException{
        String query= "INSERT INTO major (major_name,psychologist_name) VALUES (?,?)";
        try(PreparedStatement pstmt= conn.prepareStatement(query)){
            for(Major major: majors){
                pstmt.setString(1,major.name());
                pstmt.setString(2,psychologistName);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}
