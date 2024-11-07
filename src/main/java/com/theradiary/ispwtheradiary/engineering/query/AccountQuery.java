package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountQuery {
    private AccountQuery() {
    }

    public static void addCategory(Connection conn, List<Category> categories, String mail) throws SQLException {
        String query = "INSERT INTO category (category,patient) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Category category : categories) {
                pstmt.setString(1, category.name());
                pstmt.setString(2, mail);
                pstmt.addBatch();

            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addMajor(Connection conn, List<Major> majors, String mail) throws SQLException {
        String query = "INSERT INTO major (major,psicologo) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Major major : majors) {
                pstmt.setString(1, major.name());
                pstmt.setString(2, mail);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCategory(Connection conn, ArrayList<Category> categories, String mail) throws SQLException {
        {
            for(Category category:categories){
                System.out.println(category.toString());
            }
            String query = "DELETE FROM category WHERE category = ? AND patient = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (Category category : categories) {
                    pstmt.setString(1, category.name());
                    pstmt.setString(2, mail);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeMajor(Connection conn, ArrayList<Major> majors, String mail) {
        String query = "DELETE FROM major WHERE major = ? AND psicologo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Major major : majors) {
                pstmt.setString(1, major.name());
                pstmt.setString(2, mail);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
