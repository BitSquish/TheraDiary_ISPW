package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.UpdateQuery;
import com.theradiary.ispwtheradiary.model.MedicalOffice;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateDAO {
    public static void modifyMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            UpdateQuery.modifyMedicalOffice(conn, medicalOffice.getMail(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}

