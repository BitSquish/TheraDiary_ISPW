package com.theradiary.ispwtheradiary.engineering.dao.full.json;

import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.WrongEmailOrPasswordException;
import com.theradiary.ispwtheradiary.engineering.others.Printer;
import com.theradiary.ispwtheradiary.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginAndRegistrationDAOJSON implements LoginAndRegistrationDAO {
        //Filepath
        private static final String PERSISTENCE_FILE = "src/main/resources/com/theradiary/ispwtheradiary/login.json";
        List<LoggedUser> userList = new ArrayList<>();
    //Singleton
    private static LoginAndRegistrationDAOJSON instance=null;
    // Costruttore privato per evitare la creazione di nuove istanze
    private LoginAndRegistrationDAOJSON() {
        loadFile();
    }
    // Metodo per ottenere l'istanza unica
    public static LoginAndRegistrationDAOJSON getInstance() {
        if(instance==null){
            instance=new LoginAndRegistrationDAOJSON();
        }
        return instance;
    }
        private void loadFile() {
            //qui leggo il file e carico i dati in loggedUserHashMap
            try (BufferedReader reader = new BufferedReader(new FileReader(PERSISTENCE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Credentials credentials = new Credentials(data[0], data[1], Role.valueOf(data[2]));
                    if(credentials.getRole() == null)
                        throw new IllegalArgumentException("Invalid role");
                    else{
                        LoggedUser loggedUser = getLoggedUser(credentials, data);
                        userList.add(loggedUser);
                    }
                }
            } catch (IOException e) {
                Printer.errorPrint("Impossibile caricare gli utenti dal file utenti.");
            }
        }

    private static LoggedUser getLoggedUser(Credentials credentials, String[] data) {
        LoggedUser loggedUser = null;
        if(credentials.getRole().equals(Role.PSYCHOLOGIST)) {
            loggedUser = new Psychologist(credentials, data[3], data[4], data[5], data[6], Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]));

        }
        else if(credentials.getRole().equals(Role.PATIENT)) {
            loggedUser = new Patient(credentials, data[3], data[4], data[5], data[6], Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]));
        }
        return loggedUser;
    }

    @Override
    public boolean emailExists(String mail) {
        return userList.stream().anyMatch(p -> p.getCredentials().getMail().equals(mail));
    }

    @Override
    public boolean insertUser(Credentials credentials) {
        return !emailExists(credentials.getMail());
    }


    @Override
    public void registerPatient(Patient patient) throws MailAlreadyExistsException {
        if(insertUser(patient.getCredentials())){
            userList.add(patient);
            addToFile(patient);
        }
        else
           throw new MailAlreadyExistsException("Mail già registrata");
    }

    @Override
    public void registerPsychologist(Psychologist psychologist) throws MailAlreadyExistsException {
        if(insertUser(psychologist.getCredentials())){
            userList.add(psychologist);
            addToFile(psychologist);
        }
        else
            throw new MailAlreadyExistsException("Mail già registrata");
    }

    private void addToFile(LoggedUser  loggedUser ) {
        try (FileWriter writer = new FileWriter(PERSISTENCE_FILE, true)) { // 'true' to append
            Credentials credentials = loggedUser.getCredentials();

            // Costruisce la stringa da aggqingere al file
            String sb = credentials.getMail() + "," + // Mail
                    credentials.getPassword() + "," + // Password
                    credentials.getRole() + "," + // Role
                    loggedUser.getName() + "," + // Name
                    loggedUser.getSurname() + "," + // Surname
                    loggedUser.getCity() + "," + // City
                    loggedUser.getDescription() + "," + // Description
                    loggedUser.isInPerson() + "," + // InPerson
                    loggedUser.isOnline(); // Online

            writer.write(sb + "\n"); //Aggiunge l'utente al file
            loadFile(); // Ricarica i dati dopo aver aggiunto un nuovo utente
        } catch (IOException e) {
            Printer.errorPrint("Impossibile salvare l'utente sul file JSON.");
        }
    }

    @Override
    public void login(Credentials credentials) throws WrongEmailOrPasswordException {
        LoggedUser  loggedUser  = userList.stream().filter(p -> p.getCredentials().getMail().equals(credentials.getMail())).findFirst().orElse(null);
        if (loggedUser == null || !loggedUser.getCredentials().getPassword().equals(credentials.getPassword())) {
            throw new WrongEmailOrPasswordException("Mail o password errati");
        }
        credentials.setRole(loggedUser.getCredentials().getRole());
    }

    @Override
    public void retrievePatient(Patient patient) {
        Patient app = (Patient) userList.stream().filter(p -> p.getCredentials().getMail().equals(patient.getCredentials().getMail())).findFirst().orElse(null);
        if(app != null){
            setUserParameters(patient, app);
        }
    }

    @Override
    public void retrievePsychologist(Psychologist psychologist) {
        Psychologist app = (Psychologist) userList.stream().filter(p -> p.getCredentials().getMail().equals(psychologist.getCredentials().getMail())).findFirst().orElse(null);
        if(app != null){
            setUserParameters(psychologist, app);
        }
    }

    private void setUserParameters(LoggedUser user, LoggedUser app) {
        user.setName(app.getName());
        user.setSurname(app.getSurname());
        user.setCity(app.getCity());
        user.setDescription(app.getDescription());
        user.setInPerson(app.isInPerson());
        user.setOnline(app.isOnline());
    }
    @Override
    public void removePatient(Patient patient) {
        userList.removeIf(p -> p.getCredentials().getMail().equals(patient.getCredentials().getMail()));
        loadFile();
    }
}
