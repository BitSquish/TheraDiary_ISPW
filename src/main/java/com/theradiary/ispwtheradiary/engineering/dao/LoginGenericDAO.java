package com.theradiary.ispwtheradiary.engineering.dao;

import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;

import java.sql.SQLException;

public interface LoginGenericDAO {
     void login(Credentials credentials) throws SQLException, WrongEmailOrPasswordException;
}
