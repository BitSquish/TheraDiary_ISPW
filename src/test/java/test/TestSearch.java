package test;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.exceptions.NoResultException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Psychologist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TestSearch {


    //Emanuela Gallinelli, matricola 0308216

    //La ricerca funziona se si inserisce almeno uno dei 2 parametri obbligatori, ovvero città e cognome.
    private static final String PSYCHOLOGIST_NAME = "";
    private static final String PSYCHOLOGIST_SURNAME = "Cognome_psicologo";
    private static final String PSYCHOLOGIST_CITY = "Citta_psicologo";
    private static final boolean PSYCHOLOGIST_INPERSON = false; //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_ONLINE = false;   //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca
    private static final boolean PSYCHOLOGIST_PAG = false;    //Se il parametro è false vuol dire che non va inserito nei filtri di ricerca

    RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    @Test
    void testSearchWithSurname() {
        List<Psychologist> psychologists = new ArrayList<>();
        try{
            //Cerca gli psicologi compatibili con i parametri passati
            retrieveDAO.searchPsychologists(psychologists, PSYCHOLOGIST_NAME, PSYCHOLOGIST_SURNAME, "", PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE, PSYCHOLOGIST_PAG);
            //Controlla che la lista non sia vuota
            Assertions.assertFalse(psychologists.isEmpty(), "Errore: La lista di psicologi è vuota");
            for(Psychologist psychologist : psychologists){
                //Controlla che l'oggetto psychologist non sia null
                Assertions.assertNotNull(psychologist, "Errore: Lo psicologo è null");
                //Controlla che ad ogni psicologo corrispondano i parametri passati
                Assertions.assertEquals(PSYCHOLOGIST_SURNAME, psychologist.getSurname(), "Il parametro surname dello psicologo non corrisponde");
            }
        } catch (NoResultException e) {
            Assertions.fail("Errore: La ricerca non ha prodotto risultati");
        } catch (Exception e){
            Assertions.fail("Errore: " + e.getMessage());
        }
    }

    @Test
    void testWithCity(){
        List<Psychologist> psychologists = new ArrayList<>();
        try{
            //Cerca gli psicologi compatibili con i parametri passati
            retrieveDAO.searchPsychologists(psychologists, PSYCHOLOGIST_NAME, "", PSYCHOLOGIST_CITY, PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE, PSYCHOLOGIST_PAG);
            //Controlla che la lista non sia vuota
            Assertions.assertFalse(psychologists.isEmpty(), "Errore: La lista di psicologi è vuota");
            for(Psychologist psychologist : psychologists){
                //Controlla che l'oggetto psychologist non sia null
                Assertions.assertNotNull(psychologist, "Errore: Lo psicologo è null");
                //Controlla che ad ogni psicologo corrispondano i parametri passati
                Assertions.assertEquals(psychologist.getCity(), psychologist.getSurname(), "Il parametro city dello psicologo non corrisponde");
            }
        } catch (NoResultException e) {
            Assertions.fail("Errore: La ricerca non ha prodotto risultati");
        } catch (Exception e){
            Assertions.fail("Errore: " + e.getMessage());
        }
    }

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

    //Verifica che venga lanciata l'eccezione NoResultException se la ricerca non ha prodotto risultati
    @Test
    void testSearchThrowsNoResultException() {
        List<Psychologist> psychologists = new ArrayList<>();
        // Verifica che venga sollevata l'eccezione NoResultException
        Assertions.assertThrows(NoResultException.class, () -> {
            // Cerca psicologi con parametri che non corrispondono a nessun risultato
            retrieveDAO.searchPsychologists(psychologists, "", "CognomeNonPresente", "CittaNonPresente", PSYCHOLOGIST_INPERSON, PSYCHOLOGIST_ONLINE, PSYCHOLOGIST_PAG);
        });
    }
}
