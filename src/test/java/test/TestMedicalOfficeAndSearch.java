package test;

import com.theradiary.ispwtheradiary.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.patterns.factory.FactoryDAO;
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
        Assertions.assertTrue(
                medicalOffice != null &&
                        PSYCHOLOGIST_MAIL.equals(medicalOffice.getPsychologist()) &&
                        CITY.equals(medicalOffice.getCity()) &&
                        POSTCODE.equals(medicalOffice.getPostCode()) &&
                        ADDRESS.equals(medicalOffice.getAddress()) &&
                        OTHERINFO.equals(medicalOffice.getOtherInfo()),
                "Errore: I valori dell'ufficio medico non corrispondono"
        );
    }


    //Test di avvenuta modifica dello studio medico già registrato
    @Test
    void updateMedicalOffice(){
        medicalOfficeSetUp();
        medicalOffice.setPostCode("11111");
        update.modifyMedicalOffice(medicalOffice);
        retrieveDAO.retrieveMedicalOffice(medicalOffice);
        Assertions.assertTrue(
                medicalOffice.getPostCode() != null && medicalOffice.getPostCode().equals("11111"),
                "Errore: L'ufficio medico non ha il codice postale corretto"
        );
    }

    @Test
    void retrieveMedicalOfficeIfNotExists(){
        boolean medOffAlreadyInserted = retrieveDAO.retrieveMedicalOffice(medicalOffice);
        // Verifica i risultati
        Assertions.assertFalse(medOffAlreadyInserted, "Il metodo dovrebbe restituire false quando l'ufficio medico non esiste.");
    }



}
