package com.theradiary.ispwtheradiary.engineering.dao.full.sql;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.engineering.query.RetrieveQuery;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAndRegistrationDAOSQL implements LoginAndRegistrationDAO {
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String CITY = "city";
    private static final String DESCRIPTION = "description";
    private static final String IN_PERSON = "inPerson";
    private static final String ONLINE = "online";
    private static final String PAG = "pag";
    private static final String PSYCHOLOGIST = "psychologist";
    @Override
    public void login(Credentials credentials) throws WrongEmailOrPasswordException {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = LoginAndRegistrationQuery.logQuery(conn, credentials)) {
            if (rs.next()) {
                credentials.setRole(Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
    }
    //controllo se l'email è presente o meno
    @Override
    public boolean emailExists(String mail)  {
        try (Connection conn = ConnectionFactory.getConnection()){
            int rs = LoginAndRegistrationQuery.checkMail(conn, mail);
            if (rs != 0)
                return true;
        }catch (SQLException | DatabaseOperationException e){
            handleException(e);

        }
        return false;
    }
    //inserisco l'utente (credenziali) nel database
    public boolean insertUser(Credentials credentials)  {
        try (Connection conn = ConnectionFactory.getConnection()) {
            int rs = LoginAndRegistrationQuery.registerUser(conn, credentials);
            return rs != 0;
        }catch (SQLException |DatabaseOperationException e){
            handleException(e);
            return false;
        }
    }

    @Override
    public void registerPatient(Patient patient)throws MailAlreadyExistsException {
        if(emailExists(patient.getCredentials().getMail())) {
            throw new MailAlreadyExistsException(("Mail già registrata"));
        }//inserisco la password e l'email in user
        boolean flag = insertUser(patient.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()){
                LoginAndRegistrationQuery.registerPatient(conn, patient);
            }
            catch(SQLException | DatabaseOperationException e){
               handleException(e);
            }
        }

    }


    @Override
    public void registerPsychologist(Psychologist psychologist) throws  MailAlreadyExistsException {//stessa cosa che ho fatto sopra ma per lo psicologo
        if (emailExists(psychologist.getCredentials().getMail())) {
            throw new MailAlreadyExistsException("Mail già presente");
        }
        boolean flag = insertUser(psychologist.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()) {
                LoginAndRegistrationQuery.registerPsychologist(conn, psychologist);
            }
            catch(SQLException | DatabaseOperationException e){
                handleException(e);
            }
        }

    }
    @Override
    public void retrievePatient(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrievePatient(conn, patient.getCredentials().getMail())) {
            if (rs.next()) {
                patient.setName(rs.getString(NAME));
                patient.setSurname(rs.getString(SURNAME));
                patient.setCity(rs.getString(CITY));
                patient.setDescription(rs.getString(DESCRIPTION));
                patient.setInPerson(rs.getBoolean(IN_PERSON));
                patient.setOnline(rs.getBoolean(ONLINE));
                patient.setPag(rs.getBoolean(PAG));
                patient.setPsychologist(new Psychologist(new Credentials(rs.getString(PSYCHOLOGIST), Role.PSYCHOLOGIST)));
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }
    @Override
    public void retrievePsychologist(Psychologist psychologist) {
        try (Connection conn = ConnectionFactory.getConnection();
             ResultSet rs = RetrieveQuery.retrievePsychologist(conn, psychologist.getCredentials().getMail())) {
            if (rs.next()) {
                psychologist.setName(rs.getString(NAME));
                psychologist.setSurname(rs.getString(SURNAME));
                psychologist.setCity(rs.getString(CITY));
                psychologist.setDescription(rs.getString(DESCRIPTION));
                psychologist.setInPerson(rs.getBoolean(IN_PERSON));
                psychologist.setOnline(rs.getBoolean(ONLINE));
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }
    @Override
    public void removePatient(Patient patient) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            LoginAndRegistrationQuery.removePatient(conn, patient.getCredentials().getMail());
        } catch (SQLException | DatabaseOperationException e) {
            handleException(e);
        }
    }


    private void handleException(Exception e) {
        Printer.errorPrint(String.format("%s", e.getMessage()));
    }




}
