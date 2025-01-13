package com.theradiary.ispwtheradiary.engineering.dao.full.sql;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.PersistenceOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
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
    //controllo se l'email è presente o meno
    @Override
    public boolean emailExists(String mail) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()){
            int rs = LoginAndRegistrationQuery.checkMail(conn, mail);
            if (rs != 0)
                return true;
        }
        return false;
    }
    //inserisco l'utente (credenziali) nel database
    public boolean insertUser(Credentials credentials) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            int rs = LoginAndRegistrationQuery.registerUser(conn, credentials);
            return rs != 0;
        }
    }

    @Override
    public void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException {
        if(emailExists(patient.getCredentials().getMail())) {
            throw new MailAlreadyExistsException(("Mail già registrata"));
        }//inserisco la password e l'email in user
        boolean flag = insertUser(patient.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()){
                LoginAndRegistrationQuery.registerPatient(conn, patient);
            }
            catch(SQLException e){
                throw new PersistenceOperationException(REGISTER_ERROR, e);
            }
        }
        else
            throw new SQLException(); //DA SOSTITUIRE CON ECCEZIONE SPECIFICA (O FORSE NO?)
    }


    @Override
    public void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException {//stessa cosa che ho fatto sopra ma per lo psicologo
        if (emailExists(psychologist.getCredentials().getMail())) {
            throw new MailAlreadyExistsException("Mail già presente");
        }
        boolean flag = insertUser(psychologist.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()) {
                LoginAndRegistrationQuery.registerPsychologist(conn, psychologist);
            }
            catch(SQLException e){
                throw new PersistenceOperationException(REGISTER_ERROR, e);
            }
        }
        else
            throw new SQLException(); //DA SOSTITUIRE CON ECCEZIONE SPECIFICA PER INSERIMENTO SU UTENTI NON A BUON FINE (O FORSE NO?)
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
            throw new PersistenceOperationException("Errore nel recupero del paziente", e);
        }
    }

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
            throw new PersistenceOperationException("Errore nel recupero dello psicologo", e);
        }
    }




}
