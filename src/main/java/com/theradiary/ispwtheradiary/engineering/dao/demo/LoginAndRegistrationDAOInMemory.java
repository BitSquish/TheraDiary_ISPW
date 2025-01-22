package com.theradiary.ispwtheradiary.engineering.dao.demo;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.dao.demo.shared.SharedResources;
import com.theradiary.ispwtheradiary.engineering.exceptions.*;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import com.theradiary.ispwtheradiary.model.Psychologist;

import java.util.Map;
import java.util.function.Consumer;

public class LoginAndRegistrationDAOInMemory implements LoginAndRegistrationDAO {
    // Strutture dati in memoria


    @Override
    public boolean emailExists(String mail) {
        return SharedResources.getInstance().getUserTable().containsKey(normalizeEmail(mail));
    }

    private String normalizeEmail(String mail) {
        return mail.trim().toLowerCase();
    }

    @Override
    public void login(Credentials credentials) throws WrongEmailOrPasswordException {
        Credentials storedCredentials = SharedResources.getInstance().getUserTable().get(credentials.getMail());
        if (storedCredentials == null || !storedCredentials.getMail().equals(credentials.getMail())) {
            throw new WrongEmailOrPasswordException("Email o password errati");
        }
        credentials.setRole(storedCredentials.getRole());
    }

    @Override
    public boolean insertUser(Credentials credentials) {
        return SharedResources.getInstance().getUserTable().putIfAbsent(credentials.getMail(), credentials) == null;
    }

    @Override
    public void registerPatient(Patient patient) throws MailAlreadyExistsException, LoginAndRegistrationException {
        registerEntity(
                patient.getCredentials(),
                patient.getCredentials().getMail(),
                SharedResources.getInstance().getPatients(),
                patient
        );

    }

    @Override
    public void registerPsychologist(Psychologist psychologist) throws LoginAndRegistrationException, MailAlreadyExistsException {
        registerEntity(
                psychologist.getCredentials(),
                psychologist.getCredentials().getMail(),
                SharedResources.getInstance().getPsychologists(),
                psychologist
        );


    }

    private <T> void registerEntity(Credentials credentials, String email, Map<String, T> entityTable, T entity)
            throws MailAlreadyExistsException, LoginAndRegistrationException {
        if (emailExists(email)) {
            throw new MailAlreadyExistsException("Mail già esistente");
        }
        if (insertUser(credentials)) {
            entityTable.put(email, entity);
        } else {
            throw new LoginAndRegistrationException("Errore nella registrazione");
        }
    }

    @Override
    public void retrievePatient(Patient patient) throws NoResultException {
        retrieveEntity(SharedResources.getInstance().getPatients(), patient.getCredentials().getMail(), patient, storedPatient -> {
            patient.setName(storedPatient.getName());
            patient.setSurname(storedPatient.getSurname());
            patient.setCity(storedPatient.getCity());
            patient.setDescription(storedPatient.getDescription());
            patient.setInPerson(storedPatient.isInPerson());
            patient.setOnline(storedPatient.isOnline());
            patient.setPag(storedPatient.isPag());
            patient.setPsychologist(storedPatient.getPsychologist());
        });
    }

    @Override
    public void retrievePsychologist(Psychologist psychologist) throws NoResultException {
            if (psychologist == null || psychologist.getCredentials() == null || psychologist.getCredentials().getMail() == null) {
                throw new IllegalArgumentException("Psychologist or credentials or email is null");
            }
            retrieveEntity(SharedResources.getInstance().getPsychologists(), psychologist.getCredentials().getMail(), psychologist, storedPsychologist -> {
                psychologist.setName(storedPsychologist.getName());
                psychologist.setSurname(storedPsychologist.getSurname());
                psychologist.setCity(storedPsychologist.getCity());
                psychologist.setDescription(storedPsychologist.getDescription());
                psychologist.setPag(storedPsychologist.isPag());
                psychologist.setOnline(storedPsychologist.isOnline());
                psychologist.setInPerson(storedPsychologist.isInPerson());
            });

    }

    // Metodo generico per recuperare un'entità da una tabella
    private <T> void retrieveEntity(Map<String, T> entityTable, String email, T entity, Consumer<T> populateFields) // <T> è un tipo generico
            throws NoResultException { // parametri: tabella, email, entità, funzione per popolare i campi
        T storedEntity = entityTable.get(email);
        if (storedEntity == null) {
            throw new NoResultException(entity.getClass().getSimpleName() + " non trovato");
        }
        populateFields.accept(storedEntity); // popola i campi dell'entità
    }

}



