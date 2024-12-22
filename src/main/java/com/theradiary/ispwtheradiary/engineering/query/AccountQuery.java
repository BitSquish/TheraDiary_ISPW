package com.theradiary.ispwtheradiary.engineering.query;

import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountQuery {
    private AccountQuery() {
    }

    public static boolean addCategory(Connection conn, String category, String mail) throws SQLException {
        String query = "INSERT INTO category (category,patient) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category);
            pstmt.setString(2, mail);
            //restituisce il numero di righe aggiunte
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nell'aggiunta della categoria",e);
        }
    }


    public static boolean addMajor(Connection conn, String major, String mail) throws SQLException {
        String query = "INSERT INTO major (major,psychologist) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, major);
            pstmt.setString(2, mail);
            //restituisce il numero di righe eliminate
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nell'aggiunta della specializzazione",e);
        }
    }

    public static boolean removeCategory(Connection conn, String mail, String category) throws SQLException {
        String query = "DELETE FROM category WHERE category = ? AND patient = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, category);
                pstmt.setString(2, mail);
                //restituisce il numero di righe eliminate
                return pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                throw new PersistenceOperationException("Errore nella rimozione della categoria",e);
            }
    }


    public static boolean removeMajor(Connection conn, String mail, String major) {
        String query = "DELETE FROM major WHERE major = ? AND psychologist = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, major);
            pstmt.setString(2, mail);
            //restituisce il numero di righe eliminate
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new PersistenceOperationException("Errore nella rimozione della specializzazione",e);
        }

    }
}
