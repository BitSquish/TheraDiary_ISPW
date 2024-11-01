package com.theradiary.ispwtheradiary.engineering.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateQuery {
    public static void modifyMedicalOffice(Connection conn, String mail, String city, String postCode, String address, String otherInfo) throws SQLException {
        String query = "UPDATE medicaloffice SET city = ?, postCode = ?, address = ?, otherInfo = ? WHERE mail = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, city);
        pstmt.setString(2, postCode);
        pstmt.setString(3, address);
        pstmt.setString(4, otherInfo);
        pstmt.setString(5, mail);  // This is the parameter for the WHERE clause
        pstmt.executeUpdate();
        //Possono esserci problemi da gestire?
    }
}
