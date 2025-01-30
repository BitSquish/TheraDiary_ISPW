package test;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import com.theradiary.ispwtheradiary.model.Psychologist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestMedicalOfficeAndSearch {
    //Emanuela Gallinelli, matricola 0308216

    RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    UpdateDAO update = FactoryDAO.getUpdateDAO();

    /****************PARAMETRI PER IL TEST DI ALCUNI METODI DI MEDICALOFFICE****************/
    private static final String PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL = "test1@mail.com";
    private static final String CITY = "Citta_psicologo";
    private static final String POSTCODE = "00000";
    private static final String ADDRESS = "Indirizzo";
    private static final String OTHERINFO = "Informazioni";

    /****************PARAMETRI PER IL TEST DI ALCUNI METODI DI SEARCH****************/

    //La ricerca funziona se si inserisce almeno uno dei 2 parametri obbligatori, ovvero città e cognome.
    private static final String PSYCHOLOGIST_NAME = "";

    private static final String PSYCHOLOGIST_SURNAME = "Cognome_psicologo";
    private static final String PSYCHOLOGIST_CITY = "Citta_psicologo";
    private static final boolean PSYCHOLOGIST_INPERSON = false; //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_ONLINE = false;   //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_PAG = false;    //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca


    public MedicalOffice createMedicalOffice(String mail){
        return new MedicalOffice(mail, CITY, POSTCODE, ADDRESS, OTHERINFO);
    }

    //Test di inserimento di uno studio medico

    @Test
    void retrieveMedicalOfficeIfExists(){
        try {
            MedicalOffice medicalOffice = createMedicalOffice(PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL);
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
        }

    }

    //Test di avvenuta modifica dello studio medico già registrato
    @Test
    void updateMedicalOffice(){
        try{
            MedicalOffice medicalOffice = createMedicalOffice(PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL);
            medicalOffice.setPostCode("11111");
            update.modifyMedicalOffice(medicalOffice);
            Assertions.assertEquals("11111", medicalOffice.getPostCode());
        }
        catch(Exception e){
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        }
    }

    //Test ricerca psicologi con cognome e città
    @Test
    void testWithSurnameAndCity(){
        List<Psychologist> psychologists = new ArrayList<>();
        try{
            //Cerca gli psicologi compatibili con i parametri passati
            retrieveDAO.searchPsychologists(psychologists, PSYCHOLOGIST_NAME, PSYCHOLOGIST_SURNAME, PSYCHOLOGIST_CITY, PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE, PSYCHOLOGIST_PAG);
            //Controlla che la lista non sia vuota
            Assertions.assertFalse(psychologists.isEmpty(), "Errore: La lista di psicologi è vuota");
            for(Psychologist psychologist : psychologists){
                //Controlla che l'oggetto psychologist non sia null
                Assertions.assertNotNull(psychologist, "Errore: Lo psicologo è null");
                //Controlla che ad ogni psicologo corrispondano i parametri passati
                Assertions.assertEquals(PSYCHOLOGIST_SURNAME, psychologist.getSurname(), "Il parametro surname dello psicologo non corrisponde");
                Assertions.assertEquals(PSYCHOLOGIST_CITY, psychologist.getCity(), "Il parametro city dello psicologo non corrisponde");
            }
        } catch (NoResultException e) {
            Assertions.fail("Errore: La ricerca non ha prodotto risultati");
        } catch (Exception e){
            Assertions.fail("Errore: " + e.getMessage());
        }
    }

}
