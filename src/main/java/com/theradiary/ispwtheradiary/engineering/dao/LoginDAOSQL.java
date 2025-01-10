package com.theradiary.ispwtheradiary.engineering.dao;


import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.model.Credentials;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAOSQL implements LoginGenericDAO {

    public void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = LoginAndRegistrationQuery.logQuery(conn, credentials)) {
            if (rs.next()) {
                credentials.setRole(Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
    }
}
