package test;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import org.junit.jupiter.api.*;



class TestMedicalOfficeAndSearch {
    //Emanuela Gallinelli, matricola 0308216



    /****************PARAMETRI PER IL TEST DI ALCUNI METODI DI MEDICALOFFICE****************/
    private static final String CITY = "Citta_psicologo";
    private static final String POSTCODE = "00000";
    private static final String ADDRESS = "Indirizzo";
    private static final String OTHERINFO = "Informazioni";
    private static final String PSYCHOLOGIST_MAIL = "testpsicologomail@mail.com";


    private MedicalOffice medicalOffice;
    private RetrieveDAO retrieveDAO;
    private UpdateDAO update;


    @BeforeEach
    void setUp() {
        retrieveDAO = FactoryDAO.getRetrieveDAO();
        update = FactoryDAO.getUpdateDAO();
        medicalOffice = new MedicalOffice(PSYCHOLOGIST_MAIL, CITY, POSTCODE, ADDRESS, OTHERINFO);
    }

    private void medicalOfficeSetUp() {
        update.registerMedicalOffice(medicalOffice);
    }

    @AfterEach
    void tearDown(){
        update.deleteMedicalOffice(medicalOffice);
    }

    @Test
    void registerMedicalOffice(){
        medicalOfficeSetUp();
        retrieveDAO.retrieveMedicalOffice(medicalOffice);
        Assertions.assertNotNull(medicalOffice, "Errore: L'ufficio medico è null");
        Assertions.assertEquals(PSYCHOLOGIST_MAIL, medicalOffice.getPsychologist(), "Errore: La mail non corrisponde");
        Assertions.assertEquals(CITY, medicalOffice.getCity(), "Errore: La città non corrisponde");
        Assertions.assertEquals(POSTCODE, medicalOffice.getPostCode(), "Errore: Il cap non corrisponde");
        Assertions.assertEquals(ADDRESS, medicalOffice.getAddress(), "Errore: L'indirizzo non corrisponde");
        Assertions.assertEquals(OTHERINFO, medicalOffice.getOtherInfo(), "Errore: Le altre informazioni non corrispondono");
    }


    //Test di avvenuta modifica dello studio medico già registrato
    @Test
    void updateMedicalOffice(){
        medicalOfficeSetUp();
        medicalOffice.setPostCode("11111");
        update.modifyMedicalOffice(medicalOffice);
        retrieveDAO.retrieveMedicalOffice(medicalOffice);
        Assertions.assertNotNull(medicalOffice.getPostCode(), "Errore: L'ufficio medico è null");
        Assertions.assertEquals("11111", medicalOffice.getPostCode());
    }

    @Test
    void retrieveMedicalOfficeIfNotExists(){
        boolean medOffAlreadyInserted = retrieveDAO.retrieveMedicalOffice(medicalOffice);
        // Verifica i risultati
        Assertions.assertFalse(medOffAlreadyInserted, "Il metodo dovrebbe restituire false quando l'ufficio medico non esiste.");
    }



}
