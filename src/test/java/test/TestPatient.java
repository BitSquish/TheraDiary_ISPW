package test;

import com.theradiary.ispwtheradiary.engineering.dao.*;
import com.theradiary.ispwtheradiary.engineering.enums.Category;
import com.theradiary.ispwtheradiary.engineering.enums.Role;
import com.theradiary.ispwtheradiary.engineering.exceptions.LoginAndRegistrationException;
import com.theradiary.ispwtheradiary.engineering.exceptions.MailAlreadyExistsException;
import com.theradiary.ispwtheradiary.engineering.patterns.factory.FactoryDAO;
import com.theradiary.ispwtheradiary.model.Credentials;
import com.theradiary.ispwtheradiary.model.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;


class TestPatient {

    /*  Sofia Tosti 0308023 */


    private static final String TEST_MAIL = "test@gmail.com";
    private String password;
    private static final String TEST_DIARY_CONTENT = "Test content";
    private static final String TEST_TASK_NAME = "Test task";
    private static final String TEST_SURNAME = "TestSurname";
    private static final String TEST_CITY = "TestCity";
    private static final String TEST_DESCRIPTION = "TestDescription";
    private static final boolean TEST_INPERSON = true;
    private static final boolean TEST_ONLINE = false;

    public Patient createPatient(){
        return new Patient(
                new Credentials(TEST_MAIL, password, Role.PATIENT),
                TEST_TASK_NAME,
                TEST_SURNAME,
                TEST_CITY,
                TEST_DESCRIPTION,
                TEST_INPERSON,
                TEST_ONLINE

        );
    }

    //testo il metodo di login per le credenziali
    @Test
    void testCredential() {
        int res ;
        try {
            password = generatePassword();
            // Prima registriamo il paziente
            registerTester();

            // Ora tentiamo di effettuare il login con le stesse credenziali
            LoginAndRegistrationDAO loginAndRegistrationDAO = FactoryDAO.getDAO();
            Credentials credenziali = new Credentials(TEST_MAIL, password, Role.PATIENT);

            // Proviamo a fare il login
            loginAndRegistrationDAO.login(credenziali);

            // Se arriviamo qui, il login è stato eseguito correttamente
            res = 1;

        } catch (Exception e) {
            // Se c'è stata un'eccezione, segnaliamo che il test è fallito
            res = 0;
        }

        // Verifica che res sia uguale a 1 (test passato)
        Assertions.assertEquals(1, res);
    }

    // Metodo per generare una password unica
    private String generatePassword() {
        return "test" + System.currentTimeMillis();
    }

    // Metodo che si occupa di registrare il paziente
    public void registerTester(){
        LoginAndRegistrationDAO loginAndRegistrationDAO = FactoryDAO.getDAO();
        try {
            // Tentiamo la registrazione con i dati forniti
            Patient patient = new Patient(new Credentials(TEST_MAIL, password, Role.PATIENT), TEST_TASK_NAME, TEST_SURNAME, TEST_CITY, TEST_DESCRIPTION, TEST_INPERSON, TEST_ONLINE);
            loginAndRegistrationDAO.registerPatient(patient);

        } catch (LoginAndRegistrationException e) {
            // Gestiamo l'eccezione generica di registrazione
            Assertions.fail("Test fallito a causa di un'eccezione inattesa: " + e.getMessage());
        } catch (MailAlreadyExistsException e) {
            //Gestisco l'eccezione se è dovuta al fatto che l'email è già presente nel sistema
            Assertions.fail("Test fallito mail considerata già esistente: " + e.getMessage());
        }
    }
    //testo il metodo di inserimento del diario
    @Test
    void testDiary() {
        // Creazione diretta di un Patient
       Patient patient=createPatient();

        // Simulated environment
        LocalDate today = LocalDate.now();
        TaskAndToDoDAO taskAndToDoDAO = FactoryDAO.getTaskAndToDoDAO();

        try {
            // Salva il diario per oggi
            taskAndToDoDAO.diary(patient, TEST_DIARY_CONTENT, today);

            // Recupera il diario per oggi
            Optional<String> retrievedDiaryOpt = taskAndToDoDAO.getDiaryForToday(patient);
            String retrievedDiary = retrievedDiaryOpt.orElse("");

            // Asserts
            Assertions.assertEquals(TEST_DIARY_CONTENT, retrievedDiary, "Il contenuto del diario recuperato non corrisponde a quello previsto.");

        } catch (Exception e) {
            Assertions.fail("Test fallito a causa di un'eccezione inattesa: " + e.getMessage());
        }
    }
    //test del metodo per lil recupero delle categorie del paziente
    @Test
    void testRetrieveCategories(){
        Patient patient=createPatient(); // Creazione diretta di un Patient
        Category category=Category.OTHER; // Creazione di una categoria
        CategoryAndMajorDAO categoryAndMajorDAO = FactoryDAO.getCategoryAndMajorDAO(); // Simulated environment
        RetrieveDAO retrieveDAO = FactoryDAO.getRetrieveDAO();
        categoryAndMajorDAO.addCategory(patient, category);
        try{
            retrieveDAO.retrieveCategories(patient); // Recupera le categorie del paziente
            Assertions.assertTrue(patient.getCategories().contains(category), "La categoria aggiunta non è presente nella lista delle categorie del paziente.");
        }catch (Exception e){
            Assertions.fail("Test fallito a causa di un'eccezione inattesa: " + e.getMessage());
        }
    }




}










