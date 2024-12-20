package com.theradiary.ispwtheradiary.engineering.dao;



import com.theradiary.ispwtheradiary.engineering.exceptions.DatabaseOperationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.others.ConnectionFactory;
import com.theradiary.ispwtheradiary.engineering.query.LoginAndRegistrationQuery;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;


import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationDAO {
    private RegistrationDAO() {
    }
    protected static final String REGISTER_ERROR="Errore nella registrazione";
    //controllo se l'email è presente o meno
    private static boolean emailExists(String mail) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()){
            int rs = LoginAndRegistrationQuery.checkMail(conn, mail);
             if (rs != 0)
                    return true;
        }
        return false;
    }
    //inserisco l'utente (credenziali) nel database
    public static boolean insertUser(Credentials credentials) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            int rs = LoginAndRegistrationQuery.registerUser(conn, credentials);
            return rs != 0;
        }
    }

    //CREI IL BEAN NEL CONTROLLER GRAFICO E LO PASSI ALL'APPLICATIVO
    //CREI L'ISTANZA NELL'APPLICATIVO COPIANDOLO DAL BEAN E LO PASSI AL DAO
    //NEL DAO MODIFICHI L'ENTITA'
    public static void registerPatient(Patient patient) throws SQLException, MailAlreadyExistsException {
        if(emailExists(patient.getCredentials().getMail())) {
            throw new MailAlreadyExistsException(("Mail già registrata"));
        }//inserisco la password e l'email in user
        boolean flag = insertUser(patient.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()){
                LoginAndRegistrationQuery.registerPatient(conn, patient);
            }
            catch(SQLException e){
                throw new DatabaseOperationException(REGISTER_ERROR, e);
            }
        }
        else
            throw new SQLException(); //DA SOSTITUIRE CON ECCEZIONE SPECIFICA (O FORSE NO?)
    }


    public static void registerPsychologist(Psychologist psychologist) throws SQLException, MailAlreadyExistsException {//stessa cosa che ho fatto sopra ma per lo psicologo
        if (emailExists(psychologist.getCredentials().getMail())) {
            throw new MailAlreadyExistsException("Mail già presente");
        }
        boolean flag = insertUser(psychologist.getCredentials());
        if(flag){
            try (Connection conn = ConnectionFactory.getConnection()) {
                LoginAndRegistrationQuery.registerPsychologist(conn, psychologist);
            }
            catch(SQLException e){
                    throw new DatabaseOperationException(REGISTER_ERROR, e);
            }
        }
        else
            throw new SQLException(); //DA SOSTITUIRE CON ECCEZIONE SPECIFICA PER INSERIMENTO SU UTENTI NON A BUON FINE (O FORSE NO?)
    }

    public static void registerMedicalOffice(MedicalOffice medicalOffice) throws SQLException {
        try(Connection conn = ConnectionFactory.getConnection()){
            LoginAndRegistrationQuery.registerMedicalOffice(conn, medicalOffice.getPsychologist(), medicalOffice.getCity(), medicalOffice.getPostCode(), medicalOffice.getAddress(), medicalOffice.getOtherInfo());
        } catch(SQLException e){
            throw new DatabaseOperationException(REGISTER_ERROR, e);
        }
    }



}


