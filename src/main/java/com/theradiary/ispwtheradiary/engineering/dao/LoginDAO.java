package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.model.Credentials;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

//I BEAN NON VANNO PASSATI NEL DAO


public class LoginDAO {
    private LoginDAO() {
    }

    public static void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = LoginAndRegistrationQuery.logQuery(conn, credentials)) {
            if (rs.next()) {
                credentials.setRole(Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
    }
}
