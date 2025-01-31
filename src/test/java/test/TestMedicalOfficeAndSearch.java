package test;

import com.theradiary.ispwtheradiary.engineering.dao.LoginAndRegistrationDAO;
import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Psychologist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestMedicalOfficeAndSearch {
    //Emanuela Gallinelli, matricola 0308216

    RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    UpdateDAO update = FactoryDAO.getUpdateDAO();
    LoginAndRegistrationDAO loginAndRegistrationDAO = FactoryDAO.getDAO();

    /****************PARAMETRI PER IL TEST DI ALCUNI METODI DI MEDICALOFFICE****************/
    private static final String CITY = "Citta_psicologo";
    private static final String POSTCODE = "00000";
    private static final String ADDRESS = "Indirizzo";
    private static final String OTHERINFO = "Informazioni";

    /****************PARAMETRI PER IL TEST DI ALCUNI METODI DI SEARCH****************/

    //La ricerca funziona se si inserisce almeno uno dei 2 parametri obbligatori, ovvero città e cognome.
    private static final String PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL = "test1@mail.com";
    private static final String PASSWORD = "password";
    private static final String PSYCHOLOGIST_NAME = "";
    private static final String PSYCHOLOGIST_SURNAME = "Cognome_psicologo";
    private static final String PSYCHOLOGIST_CITY = "Citta_psicologo";
    private static final boolean PSYCHOLOGIST_INPERSON = false; //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_ONLINE = false;   //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_PAG = false;    //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final String PSYCHOLOGIST_DESCRIPTION = "Descrizione_psicologo";
    private Psychologist psychologist;

    private MedicalOffice medicalOffice;

    @BeforeEach
    void setUp() {
        psychologist = new Psychologist(new Credentials(PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL, PASSWORD, Role.PSYCHOLOGIST), PSYCHOLOGIST_NAME, PSYCHOLOGIST_SURNAME, PSYCHOLOGIST_CITY, PSYCHOLOGIST_DESCRIPTION, PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE);
        psychologist.setPag(PSYCHOLOGIST_PAG);
        medicalOffice = new MedicalOffice(psychologist.getCredentials().getMail(), CITY, POSTCODE, ADDRESS, OTHERINFO);
    }
    private void psychologistSetUp() {
        try{
            loginAndRegistrationDAO.insertUser(psychologist.getCredentials());
            loginAndRegistrationDAO.insertUser(psychologist.getCredentials());
            loginAndRegistrationDAO.registerPsychologist(psychologist);
        } catch (MailAlreadyExistsException e) {
            Assertions.fail("Mail già esistente: " + e.getMessage());
        } catch (LoginAndRegistrationException e) {
            Assertions.fail("Errore nel DAO: " + e.getMessage());
        }

    }

    private void medicalOfficeSetUp() {
        try{
            psychologistSetUp();
            update.registerMedicalOffice(medicalOffice);
        } catch (Exception e) {
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        }
    }

   /* @Test
    void retrieveMedicalOfficeIfExists(){
        try {
            medicalOfficeSetUp();
            boolean medOffAlreadyInserted = retrieveDAO.retrieveMedicalOffice(medicalOffice);
            // Verifica i risultati
            Assertions.assertTrue(medOffAlreadyInserted, "Il metodo dovrebbe restituire true quando l'ufficio medico esiste.");
            Assertions.assertEquals(PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL, medicalOffice.getPsychologist());
            Assertions.assertEquals(CITY, medicalOffice.getCity());
            Assertions.assertEquals(POSTCODE, medicalOffice.getPostCode());
            Assertions.assertEquals(ADDRESS, medicalOffice.getAddress());
            Assertions.assertEquals(OTHERINFO, medicalOffice.getOtherInfo());
        }catch(Exception e){
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        } finally {
            update.deleteUser(psychologist.getCredentials());
            update.deletePsychologist(psychologist);
            update.deleteMedicalOffice(medicalOffice);
        }
    }


    //Test di avvenuta modifica dello studio medico già registrato
    @Test
    void updateMedicalOffice(){
        try{
            medicalOfficeSetUp();
            medicalOffice.setPostCode("11111");
            update.modifyMedicalOffice(medicalOffice);
            Assertions.assertEquals("11111", medicalOffice.getPostCode());
        }
        catch(Exception e){
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        }finally {
            update.deleteUser(psychologist.getCredentials());
            update.deletePsychologist(psychologist);
            update.deleteMedicalOffice(medicalOffice);
        }
    }

    //Test ricerca psicologi con cognome e città
    @Test
    void testWithSurnameAndCity(){
        List<Psychologist> psychologists = new ArrayList<>();
        try{
            //Cerca gli psicologi compatibili con i parametri passati
            retrieveDAO.searchPsychologists(psychologists, "", PSYCHOLOGIST_SURNAME, PSYCHOLOGIST_CITY, PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE, PSYCHOLOGIST_PAG);
            //Controlla che la lista non sia vuota
            Assertions.assertFalse(psychologists.isEmpty(), "Errore: La lista di psicologi è vuota");
            for(Psychologist p : psychologists){
                //Controlla che l'oggetto psychologist non sia null
                Assertions.assertNotNull(p, "Errore: Lo psicologo è null");
                //Controlla che ad ogni psicologo corrispondano i parametri passati
                Assertions.assertEquals(PSYCHOLOGIST_SURNAME, p.getSurname(), "Il parametro surname dello psicologo non corrisponde");
                Assertions.assertEquals(PSYCHOLOGIST_CITY, p.getCity(), "Il parametro city dello psicologo non corrisponde");
            }
        } catch (NoResultException e) {
            Assertions.fail("Errore: La ricerca non ha prodotto risultati");
        } catch (Exception e){
            Assertions.fail("Errore: " + e.getMessage());
        } finally {
            update.deleteUser(psychologist.getCredentials());
            update.deletePsychologist(psychologist);
        }
    }*/

    @Test
    void prova(){
        psychologistSetUp();
        Assertions.assertTrue(true);
    }

}
