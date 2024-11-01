package com.theradiary.ispwtheradiary.controller.application;

import com.theradiary.ispwtheradiary.engineering.dao.LoginDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.beans.CredentialsBean;

import java.sql.SQLException;

public class Login {
    public void log(CredentialsBean credentialsBean) throws WrongEmailOrPasswordException {
        try {
            Credentials credentials = new Credentials(credentialsBean.getMail(), credentialsBean.getPassword(), credentialsBean.getRole());
            LoginDAO.login(credentials);
            credentialsBean.setRole(credentials.getRole());
            //return cred;
        } catch(SQLException e) { //CONTROLLARE ECCEZIONI
            System.out.println("Errore: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (WrongEmailOrPasswordException e) {
            System.out.println("Errore: " + e.getMessage());
            throw new WrongEmailOrPasswordException(e.getMessage());
        }
    }
}
