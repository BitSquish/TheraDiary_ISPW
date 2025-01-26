package test;

import com.theradiary.ispwtheradiary.engineering.dao.RetrieveDAO;
import com.theradiary.ispwtheradiary.engineering.dao.UpdateDAO;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.MedicalOffice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestMedicalOffice {
    //Emanuela Gallinelli, matricola 0308216

    private static final String PSYCHOLOGIST_WITH_MEDICALOFFICE_MAIL = "test1@mail.com";
    private static final String PSYCHOLOGIST_WITHOUT_MEDICALOFFICE_MAIL = "test2@mail.com";
    private static final String CITY = "Citta_psicologo";
    private static final String POSTCODE = "00000";
    private static final String ADDRESS = "Indirizzo";
    private static final String OTHERINFO = "Informazioni";
    RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
    UpdateDAO update = FactoryDAO.getUpdateDAO();

    public MedicalOffice createMedicalOffice(String mail){
        return new MedicalOffice(mail, CITY, POSTCODE, ADDRESS, OTHERINFO);
    }

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

    @Test
    void retrieveMedicalOfficeIfNotExists(){
        try{
            MedicalOffice medicalOffice = createMedicalOffice(PSYCHOLOGIST_WITHOUT_MEDICALOFFICE_MAIL);
            boolean medOffAlreadyInserted = retrieveDAO.retrieveMedicalOffice(medicalOffice);
            // Verifica i risultati
            Assertions.assertFalse(medOffAlreadyInserted, "Il metodo dovrebbe restituire false quando l'ufficio medico non esiste.");
        }
        catch(Exception e){
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        }

    }

    @Test
    void registerMedicalOffice(){
        try{
            MedicalOffice medicalOffice = createMedicalOffice(PSYCHOLOGIST_WITHOUT_MEDICALOFFICE_MAIL);
            update.registerMedicalOffice(medicalOffice);
        }
        catch(Exception e){
            Assertions.fail("Errore imprevisto: " + e.getMessage());
        }
    }

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

}
